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
public class CarCrewDAO extends MySQLDAO {
    /**
     * @throws InternalDAOException
     */
    protected CarCrewDAO() throws InternalDAOException {
        super();
        nameTable = " `Car_crew` ";
    }

    public CarCrew[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {

        ArrayList<CarCrew> carCrews = new ArrayList();

        String search_crew = "select C.id as id_crew, C.id_route, T.* from Car_crew C, Truck T " +
                "where C.id_car=T.id LIMIT "  + (page-1)*itemsPerPage + "," + itemsPerPage;

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search_crew);

            try {
                while (resultSet.next()) {
                    CarCrew carCrew = new CarCrew();

                    carCrew.setId(resultSet.getInt("id_crew"));
                    RouteArray routeArray = new RouteArray(resultSet.getInt("id_route"));
                    RouteDAO routeDAO = new RouteDAO();
                    routeDAO.read(routeArray);
                    carCrew.setRoute(routeArray);

                    Truck truck = new Truck();
                    truck.setId(resultSet.getInt("id"));
                    truck.setNumber(resultSet.getString("number"));
                    truck.setModel(resultSet.getString("model"));
                    truck.setMax_capacity(resultSet.getFloat("max_capacity"));
                    truck.setCondition(resultSet.getBoolean("condition"));
                    carCrew.setTruck(truck);
                    DriverDAO driverDAO = new DriverDAO();
                    carCrew.setDrivers(driverDAO.getDrivers(resultSet.getInt("id_crew")));

                    carCrews.add(carCrew);
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

        return carCrews.toArray(new CarCrew[carCrews.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        CarCrew carCrew = null;

        String insert = "insert into" + nameTable +
                "(`id_car`)" +
                " values ((select id from Truck where number=? ))";

        try {
            carCrew = (CarCrew) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        preparedStatement = getPrepareStatement(insert);

        try {
            preparedStatement.setString(1, carCrew.getTruck().getNumber());

            preparedStatement.executeUpdate();

            DriverDAO driverDAO = new DriverDAO();
            driverDAO.setDrivers(carCrew.getDrivers(), carCrew);
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
        CarCrew carCrew = null;

        try {
            carCrew = (CarCrew) readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if(carCrew.getId()==0){
            throw new InvalidDataDAOException("For reading carCrew incorrectly chosen field, " +
                    "try Id");
        }

        String search = "select C.id as id_crew, C.id_route, T.* " +
                "from Car_crew C, Truck T where C.id_car=T.id and C.id=" + carCrew.getId();

        statement = getStatement();

        try {
            resultSet = statement.executeQuery(search);

            if(resultSet.first()) {

                RouteArray routeArray = new RouteArray(resultSet.getInt("id_route"));
                RouteDAO routeDAO = new RouteDAO();
                routeDAO.read(routeArray);
                carCrew.setRoute(routeArray);

                Truck truck = new Truck();
                truck.setId(resultSet.getInt("id"));
                truck.setNumber(resultSet.getString("number"));
                truck.setModel(resultSet.getString("model"));
                truck.setMax_capacity(resultSet.getFloat("max_capacity"));
                truck.setCondition(resultSet.getBoolean("condition"));
                carCrew.setTruck(truck);

                DriverDAO driverDAO = new DriverDAO();
                carCrew.setDrivers(driverDAO.getDrivers(resultSet.getInt("id_crew")));
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
        CarCrew carCrew = null;

        String update = "update" + nameTable + "set id_car=(select id from Truck where number=?), id_route=? where `id`=?";

        try {
            carCrew = (CarCrew) updateElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        preparedStatement = getPrepareStatement(update);

        try {
            preparedStatement.setString(1, carCrew.getTruck().getNumber());
            preparedStatement.setInt(2, carCrew.getRoute().getId());
            preparedStatement.setInt(3, carCrew.getId());

            preparedStatement.executeUpdate();

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            close();
        }
    }

    private class DriverDAO extends MySQLDAO{

        /**
         * @throws InternalDAOException
         */
        protected DriverDAO() throws InternalDAOException {
        }

        @Override
        public void create(Entity newElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {

        }

        @Override
        public void read(Entity readElement) throws InvalidDataDAOException, InternalDAOException {

        }

        @Override
        public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {

        }

        @Override
        public Entity[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {
            return new Entity[0];
        }

        public ArrayList<User> getDrivers(int id_crew) throws InternalDAOException, InvalidDataDAOException {
            ArrayList<User> drivers = new ArrayList();

            String search = "select U.* from Driver_Car_Crew D, `User` U where U.id=D.id_driver and id_car_crew=" + id_crew;

            statement = getStatement();

            try {
                resultSet =  statement.executeQuery(search);

                try {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setLogin(resultSet.getString("login"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setName(resultSet.getString("name"));
                        user.setLast_name(resultSet.getString("last_name"));
                        user.setGroup(resultSet.getString("group"));
                        user.setTelephone(resultSet.getString("telephone"));
                        user.setCreateDate(resultSet.getDate("create_date"));
                        drivers.add(user);
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

            return drivers;
        }
        public void setDrivers(ArrayList<User> drivers, CarCrew carCrew) throws InternalDAOException, DublicateKeyDAOException {

            statement = getStatement();

            try {
                for(User driver:drivers){
                    String insert = "insert into Driver_Car_Crew (id_driver, id_car_crew) values (" +
                            driver.getId() + ", (SELECT id from Car_crew where id_car=" +
                            "(select id from Truck where number='" + carCrew.getTruck().getNumber() + "') AND id_route=" +
                            carCrew.getRoute().getId()+ "))";
                    System.out.println(insert);

                    statement.executeUpdate(insert);
                }
            } catch (SQLException e){
                throw new DublicateKeyDAOException("setDrivers failed", e);
            }
            finally {
                close();
            }
        }
    }
}
