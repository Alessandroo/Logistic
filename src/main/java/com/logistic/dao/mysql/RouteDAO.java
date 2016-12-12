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
public class RouteDAO extends MySQLDAO {
    /**
     * @throws InternalDAOException
     */
    protected RouteDAO() throws InternalDAOException {
        super();
        nameTable = " `Route` ";
    }

    public RouteArray[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {

        ArrayList<RouteArray> routes = new ArrayList();
        ArrayList<Integer> id_routes = new ArrayList<>();

        String get_idRouts = "select id_route FROM Route GROUP BY id_route";

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(get_idRouts);

            try{
                while (resultSet.next()) {
                   id_routes.add(resultSet.getInt("id_route"));
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException(e);
            }

            for(int id_route:id_routes){
                String search_route = "select * from Route WHERE id_route=" + id_route + " ORDER BY number_in_sequence";
                resultSet = statement.executeQuery(search_route);
                RouteArray routeArray = new RouteArray(id_route);
                try{
                    while (resultSet.next()) {
                        Road road = new Road();
                        road.setId(resultSet.getInt("id_road"));
                        RoadDAO roadDAO = new RoadDAO();
                        roadDAO.read(road);

                        Order order = new Order();
                        order.setId(resultSet.getInt("id_order"));
                        OrderDAO orderDAO = new OrderDAO();
                        orderDAO.read(order);

                        routeArray.addRoute(road, order);
                    }
                    routes.add(routeArray);
                }catch (SQLException e){
                    throw new InvalidDataDAOException(e);
                }
            }
        }catch (SQLException e){
            throw new InvalidDataDAOException(e);
        }
        finally {
            close();
        }

        return routes.toArray(new RouteArray[routes.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        int id_route = 0;
        RouteArray routeArray = null;

        String get_idRoute_max = "select id_route FROM Route GROUP BY id_route order by id_route desc limit 1";

        try {
            routeArray = (RouteArray) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(get_idRoute_max);

            try{
                if(resultSet.first()) {
                    id_route = resultSet.getInt("id_route") + 1;
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException(e);
            }

            for(int i = 0; i < routeArray.getRoutes().size(); i++) {
                Route route = routeArray.getRoutes().get(i);
                String insert = "INSERT INTO Route (id_route, number_in_sequence, id_road, id_order) VALUES (" +
                        id_route + ", " + i + ", " + route.getRoad().getId() + ", " + route.getOrder().getId() + ")";

                try {
                    statement.executeUpdate(insert);
                } catch (SQLException e) {
                    throw new InvalidDataDAOException(e);
                }
            }
        }catch (SQLException e){
            throw new InvalidDataDAOException(e);
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
        RouteArray routeArray = null;

        try {
            routeArray = (RouteArray) readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in read are failed", e);
        }

        String get_idRouts = "select * from Route WHERE id_route=" + routeArray.getId() + " ORDER BY number_in_sequence";

        statement = getStatement();

        try {
            resultSet = statement.executeQuery(get_idRouts);

            try {
                while (resultSet.next()) {
                    Road road = new Road();
                    road.setId(resultSet.getInt("id_road"));
                    RoadDAO roadDAO = new RoadDAO();
                    roadDAO.read(road);

                    Order order = new Order();
                    order.setId(resultSet.getInt("id_order"));
                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.read(order);

                    routeArray.addRoute(road, order);
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

    @Override
    public void delete(Entity deleteElement) throws InternalDAOException, InvalidDataDAOException {
        String delete = "delete from" + nameTable + " where id_route =" + deleteElement.getId();

        statement = getStatement();

        try {
            statement.executeUpdate(delete);

        } catch (SQLException e) {
            throw new InvalidDataDAOException("Delete failed", e);
        }
        finally {
            close();
        }
    }
}
