package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.Entity;
import com.logistic.model.systemunits.entities.Point;
import com.logistic.model.systemunits.entities.Road;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 11.12.2016.
 */
public class RoadDAO extends MySQLDAO {
    /**
     * @throws InternalDAOException
     */
    protected RoadDAO() throws InternalDAOException {
        super();
        nameTable = " `Road` ";
    }

    public Road[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {

        ArrayList<Road> roads = new ArrayList();

        String search = "select R.*, " +
                "(select latitude from Point P where id_begin_point=P.id) as latitude_begin, " +
                "(select longitude from Point P where id_begin_point=P.id) as longitude_begin, " +
                "(select latitude from Point P where id_end_point=P.id) as latitude_end, " +
                "(select longitude from Point P where id_end_point=P.id) as longitude_end  from Road R limit " +
                (page-1)*itemsPerPage + "," + itemsPerPage;

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);

            try {
                while (resultSet.next()) {
                    Road road = new Road();
                    road.setId(resultSet.getInt("id"));
                    road.setLongest(resultSet.getFloat("long"));
                    road.setTime(resultSet.getTime("time"));
                    Point point_begin = new Point();
                    point_begin.setId(resultSet.getInt("id_begin_point"));
                    point_begin.setX(resultSet.getFloat("latitude_begin"));
                    point_begin.setY(resultSet.getFloat("longitude_begin"));
                    road.setPointBegin(point_begin);
                    Point point_end = new Point();
                    point_end.setId(resultSet.getInt("id_end_point"));
                    point_end.setX(resultSet.getFloat("latitude_end"));
                    point_end.setY(resultSet.getFloat("longitude_end"));
                    road.setPointEnd(point_end);
                    roads.add(road);
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

        return roads.toArray(new Road[roads.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        Road road = null;

        try {
            road = (Road) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        String insert_points = "INSERT INTO `Point` (`latitude`, `longitude`) VALUES ("+ road.getPointBegin().getX() +
                "," + road.getPointBegin().getY() + "), (" + road.getPointEnd().getX() + "," + road.getPointEnd().getY() + ")";

        System.out.println(insert_points);
        String insert_road = "INSERT  INTO Road (id_begin_point, id_end_point, `long`, time) VALUES ((SELECT id FROM Point WHERE latitude=" +
                road.getPointBegin().getX() +" AND longitude=" +road.getPointBegin().getY() +
                " ), (SELECT id FROM Point WHERE latitude="+ road.getPointEnd().getX() + " and longitude=" +
                road.getPointEnd().getY() + " ), " + road.getLongest() + ", " + road.getTime() + ")";
        System.out.println(insert_road);

        statement = getStatement();

        try {
            try {
                statement.executeUpdate(insert_points);
            }
            catch (SQLException e){

            }
            statement.executeUpdate(insert_road);
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
        Road road = null;

        try {
            road = (Road) readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if (road.getId() == 0){
            throw new InvalidDataDAOException("For reading road incorrectly chosen field, " +
                    "try Id");
        }

        String search = "select R.*, " +
                "(select latitude from Point P where id_begin_point=P.id) as latitude_begin, " +
                "(select longitude from Point P where id_begin_point=P.id) as longitude_begin, " +
                "(select latitude from Point P where id_end_point=P.id) as latitude_end, " +
                "(select longitude from Point P where id_end_point=P.id) as longitude_end  from Road R where " +
                "R.id=" + road.getId();

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);
            if(resultSet.first()) {
                road.setId(resultSet.getInt("id"));
                road.setLongest(resultSet.getFloat("long"));
                road.setTime(resultSet.getTime("time"));
                Point point_begin = new Point();
                point_begin.setId(resultSet.getInt("id_begin_point"));
                point_begin.setX(resultSet.getFloat("latitude_begin"));
                point_begin.setY(resultSet.getFloat("longitude_begin"));
                road.setPointBegin(point_begin);
                Point point_end = new Point();
                point_end.setId(resultSet.getInt("id_end_point"));
                point_end.setX(resultSet.getFloat("latitude_end"));
                point_end.setY(resultSet.getFloat("longitude_end"));
                road.setPointEnd(point_end);
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
        Road road = null;

        String update = "UPDATE Road SET time=?, `long`=? WHERE id=?";

        try {
            road = (Road) updateElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Cast Entity in update are failed", e);
        }

        preparedStatement = getPrepareStatement(update);

        try {
            preparedStatement.setTime(1, road.getTime());
            preparedStatement.setFloat(2, road.getLongest());
            preparedStatement.setInt(3, road.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            close();
        }
    }

    public int getRoadId(Road road) throws InternalDAOException, DublicateKeyDAOException {
        int id = 0;

        String search = "SELECT id FROM Road WHERE id_begin_point = (SELECT id FROM Point WHERE latitude=" +
        road.getPointBegin().getX() +" AND longitude=" +road.getPointBegin().getY() +
                " LIMIT 1) and id_end_point=(SELECT id FROM Point WHERE latitude="+ road.getPointEnd().getX() + " and longitude=" +
                road.getPointEnd().getY() + " LIMIT 1)";


        statement = getStatement();

        try {
            resultSet = statement.executeQuery(search);

            if(resultSet.first()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DublicateKeyDAOException((String.format("getRoadId %s failed", nameTable)), e);
        }
        finally {
            close();
        }
        return id;
    }
}
