package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.*;

import java.sql.SQLException;
import java.util.ArrayList;

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

        String search = "select O.id, calculation, id_cargo, id_client, id_road, id_timetable, " +
                "(select `name` from Delivery_class D where D.id=O.id_delivery_class) as `delivery_class`, " +
                "T.time_begin, T.time_end  from `Order` O left outer join Timetable T on O.id_timetable=T.id limit " +
                (page-1)*itemsPerPage + "," + itemsPerPage;

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
                    order.setDeliveryClass(deliveryClass);

                    TimeTable timeTable = new TimeTable();
                    timeTable.setId(resultSet.getInt("id_timetable"));
                    timeTable.setTimeBegin(resultSet.getTimestamp("time_begin"));
                    timeTable.setTimeEnd(resultSet.getTimestamp("time_end"));
                    order.setTimeTable(timeTable);

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

        String insert = "insert into " + nameTable + "(id_cargo, id_client, id_road, id_delivery_class) values " +
                "((select id from Cargo where `name`=? and weight=? and " +
                "id_type=(select id from Type_cargo where class = ?)), " +
                "?, ?, (select id from Delivery_class where `name` = ?))";

        try {
            order = (Order) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        preparedStatement = getPrepareStatement(insert);

        try {
            Cargo cargo = order.getCargo();
            CargoDAO cargoDAO = new CargoDAO();
            cargoDAO.create(cargo);

            Road road = order.getRoad();
            RoadDAO roadDAO = new RoadDAO();
            roadDAO.create(road);

            preparedStatement.setString(1, cargo.getName());
            preparedStatement.setFloat(2, cargo.getWeight());
            preparedStatement.setString(3, cargo.getTypeCargo().getName());
            preparedStatement.setInt(4, order.getClient().getId());
            preparedStatement.setInt(5, roadDAO.getRoadId(road));
            preparedStatement.setString(6, order.getDeliveryClass().getName());

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
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if (order.getId() == 0){
            throw new InvalidDataDAOException("For reading road incorrectly chosen field, " +
                    "try Id");
        }

        String search = "select O.id, calculation, id_cargo, id_client, id_road, " +
                "(select `name` from Delivery_class D where D.id=O.id_delivery_class) as `delivery_class`, " +
                "T.time_begin, T.time_end,   from `Order` O, Timetable T  where T.id = O.id_timetable and O.id=" +
                order.getId();

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);
            if(resultSet.first()) {
                order.setId(resultSet.getInt("id"));
                order.setCalculation(resultSet.getFloat("calculation"));

                DeliveryClass deliveryClass = new DeliveryClass();
                deliveryClass.setName(resultSet.getString("delivery_class"));
                order.setDeliveryClass(deliveryClass);

                TimeTable timeTable = new TimeTable();
                timeTable.setTimeBegin(resultSet.getTimestamp("time_begin"));
                timeTable.setTimeEnd(resultSet.getTimestamp("time_end"));
                order.setTimeTable(timeTable);

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
        Order order = null;

        try {
            order = (Order) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update are failed", e);
        }

        String update_time = "UPDATE Timetable set time_begin="+ order.getTimeTable().getTimeBegin() +", time_end=" +
                order.getTimeTable().getTimeEnd() + " WHERE id=" + order.getTimeTable().getId();

        String update_order = "UPDATE `Order` set calculation=" + order.getCalculation() + " WHERE id=" + order.getId();

        statement = getStatement();

        try {
            statement.executeUpdate(update_time);
            statement.executeUpdate(update_order);
            RoadDAO roadDAO = new RoadDAO();
            Road road = order.getRoad();
            road.setId(roadDAO.getRoadId(road));
            roadDAO.update(road);

        } catch (SQLException e) {
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            close();
        }
    }
}
