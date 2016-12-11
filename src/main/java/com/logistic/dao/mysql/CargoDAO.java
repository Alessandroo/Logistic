package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.Cargo;
import com.logistic.model.systemunits.entities.Entity;
import com.logistic.model.systemunits.entities.TypeCargo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 11.12.2016.
 */
public class CargoDAO extends MySQLDAO {
    /**
     * @throws InternalDAOException
     */
    protected CargoDAO() throws InternalDAOException {
        super();
        nameTable = " `Cargo` ";
    }

    public Cargo[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {

        ArrayList<Cargo> cargos = new ArrayList();

        String search = "select C.*,T.class, T.max_speed from `Cargo` C, `Type_cargo` T where C.id_type=T.id limit " + (page-1)*itemsPerPage + "," + itemsPerPage;

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);

            try {
                while (resultSet.next()) {
                    Cargo cargo = new Cargo();
                    TypeCargo typeCargo = new TypeCargo();
                    cargo.setId(resultSet.getInt("id"));
                    cargo.setWeight(resultSet.getFloat("weight"));
                    cargo.setName(resultSet.getString("name"));
                    typeCargo.setName(resultSet.getString("class"));
                    typeCargo.setMax_speed(resultSet.getInt("max_speed"));
                    cargo.setTypeCargo(typeCargo);
                    cargos.add(cargo);
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

        return cargos.toArray(new Cargo[cargos.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        Cargo cargo= null;

        String insert = "insert into" + nameTable +
                "(`weight`, `name`, `id_type`)" +
                " values (?, ?, (select id from Type_cargo where class=?))";

        try {
            cargo = (Cargo) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        preparedStatement = getPrepareStatement(insert);

        try {
            preparedStatement.setFloat(1, cargo.getWeight());
            preparedStatement.setString(2, cargo.getName());
            preparedStatement.setString(3,cargo.getTypeCargo().getName());

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
        Cargo cargo = null;

        String search = "select C.*,T.class, T.max_speed from `Cargo` C, `Type_cargo` T where C.id_type=T.id and C.id=?";

        try {
            cargo = (Cargo) readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if(cargo.getId()==0){
            throw new InvalidDataDAOException("For reading cargo incorrectly chosen field, " +
                    "try Id");
        }

        preparedStatement = getPrepareStatement(search);

        try {
            preparedStatement.setInt(1, cargo.getId());

            resultSet =  preparedStatement.executeQuery();
            if(resultSet.first()) {
                TypeCargo typeCargo = new TypeCargo();
                cargo.setId(resultSet.getInt("id"));
                cargo.setWeight(resultSet.getFloat("weight"));
                cargo.setName(resultSet.getString("name"));
                typeCargo.setName(resultSet.getString("class"));
                typeCargo.setMax_speed(resultSet.getInt("max_speed"));
                cargo.setTypeCargo(typeCargo);
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
        Cargo cargo = null;

        String update = "update" + nameTable + "set weight=?, `name`=?, id_type=(select id from Type_cargo where class=?) where `id`=?";

        try {
            cargo = (Cargo) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        preparedStatement = getPrepareStatement(update);

        try {
            preparedStatement.setFloat(1, cargo.getWeight());
            preparedStatement.setString(2, cargo.getName());
            preparedStatement.setString(2, cargo.getTypeCargo().getName());
            preparedStatement.setInt(3, cargo.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            close();
        }
    }
}
