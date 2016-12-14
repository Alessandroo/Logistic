package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.*;
import com.logistic.utils.DistanceGeo;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Vojts on 11.12.2016.
 */
public class OrderDAO extends MySQLDAO {
    /**
     * @throws InternalDAOException
     */
    protected OrderDAO() throws InternalDAOException {
        super();
        nameTable = " `Order` ";
    }

    public Order[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {

        ArrayList<Order> orders = new ArrayList();

        String search = "select O.id, calculation, id_cargo, id_client, id_road, " +
                "(select `name` from Delivery_class D where D.id=O.id_delivery_class) as `delivery_class`, " +
                "(select `price_km` from Delivery_class D where D.id=O.id_delivery_class) AS price_km " +
                "from `Order` O limit " + (page-1)*itemsPerPage + "," + itemsPerPage;

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);

            try {
                while (resultSet.next()) {
                    Order order = new Order();

                    order.setId(resultSet.getInt("id"));
                    order.setCalculation(resultSet.getFloat("calculation"));

                    System.out.println("Id " + order.getId());

                    DeliveryClass deliveryClass = new DeliveryClass();
                    deliveryClass.setName(resultSet.getString("delivery_class"));
                    deliveryClass.setPrice_km(resultSet.getFloat("price_km"));
                    order.setDeliveryClass(deliveryClass);

                    Cargo cargo = new Cargo();
                    cargo.setId(resultSet.getInt("id_cargo"));
                    CargoDAO cargoDAO = new CargoDAO();
                    cargoDAO.read(cargo);
                    order.setCargo(cargo);

                    User client = new User();
                    client.setId(resultSet.getInt("id_client"));
                    UserDAO userDAO = new UserDAO();
                    userDAO.read(client);
                    order.setClient(client);

                    Road road = new Road();
                    road.setId(resultSet.getInt("id_road"));
                    RoadDAO roadDAO = new RoadDAO();
                    roadDAO.read(road);
                    order.setRoad(road);

                    orders.add(order);
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException(e);
            }
        }catch (SQLException e){
            throw new InvalidDataDAOException(e);
        }
        finally {
            close();
        }

        return orders.toArray(new Order[orders.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        Order order = null;

        String insert = "insert into " + nameTable + "(id_cargo, id_client, id_road, id_delivery_class, calculation) " +
                "values " +
                "((select id from Cargo where `name`=? and weight=? and " +
                "id_type=(select id from Type_cargo where class = ?)), " +
                "?, ?, (select id from Delivery_class where `name` = ?), ?)";

        try {
            order = (Order) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        preparedStatement = getPrepareStatement(insert);

        try {
            Cargo cargo = order.getCargo();
            CargoDAO cargoDAO = new CargoDAO();
            cargoDAO.create(cargo);

            Road road = order.getRoad();
            DistanceGeo distanceGeo = new DistanceGeo();
            distanceGeo.setBeginPoint(road.getPointBegin());
            distanceGeo.setEndPoint(road.getPointEnd());

            road.setLongest((float) distanceGeo.getDistance());

            float route = road.getLongest(); // [km]
            float speed = (float) order.getDeliveryClass().getPrice_km(); // [km/h]
            float time = (float) (1.1 *(route / speed));
            int hours = (int) time;
            float minutes_left = 60 * (time - hours);
            int minutes = (int) (60 * (time - hours));
            int seconds = (int) (60 * (minutes_left-minutes));
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.HOUR, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, seconds);

            Time time1 = new Time(calendar.getTime().getTime());

            road.setTime(time1);

            RoadDAO roadDAO = new RoadDAO();
            roadDAO.create(road);

            preparedStatement.setString(1, cargo.getName());
            preparedStatement.setFloat(2, cargo.getWeight());
            preparedStatement.setString(3, cargo.getTypeCargo().getName());
            preparedStatement.setInt(4, order.getClient().getId());
            preparedStatement.setInt(5, roadDAO.getRoadId(road));
            preparedStatement.setString(6, order.getDeliveryClass().getName());
            preparedStatement.setFloat(7, (float) (order.getRoad().getLongest() *
                    order.getDeliveryClass().getPrice_km()));

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
        }
        finally {
            close();
        }
    }

    /**
     * @param readElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {
        Order order = null;

        try {
            order = (Order) readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if (order.getId() == 0){
            throw new InvalidDataDAOException("For reading road incorrectly chosen field, " +
                    "try Id");
        }

        String search = "select O.id, calculation, id_cargo, id_client, id_road, " +
                "(select `name` from Delivery_class D where D.id=O.id_delivery_class) as `delivery_class`, " +
                "(select `price_km` from Delivery_class D where D.id=O.id_delivery_class) " +
                "from `Order` O WHERE O.id=" +
                order.getId();

        System.out.println(search);

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);
            if(resultSet.first()) {
                order.setId(resultSet.getInt("id"));
                order.setCalculation(resultSet.getFloat("calculation"));

                DeliveryClass deliveryClass = new DeliveryClass();
                deliveryClass.setName(resultSet.getString("delivery_class"));
                deliveryClass.setPrice_km(resultSet.getFloat("price_km"));
                order.setDeliveryClass(deliveryClass);

                Cargo cargo = new Cargo();
                cargo.setId(resultSet.getInt("id_cargo"));
                CargoDAO cargoDAO = new CargoDAO();
                cargoDAO.read(cargo);
                order.setCargo(cargo);

                User client = new User();
                client.setId(resultSet.getInt("id_client"));
                UserDAO userDAO = new UserDAO();
                userDAO.read(client);
                order.setClient(client);

                Road road = new Road();
                road.setId(resultSet.getInt("id_road"));
                RoadDAO roadDAO = new RoadDAO();
                roadDAO.read(road);
                order.setRoad(road);
            }
            else{
                throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
            }
        }catch (SQLException e){
            throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
        }
        finally {
            close();
        }
    }


    /**
     * @param updateElement
     * @throws DublicateKeyDAOException
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        return;
    }
}
