package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction tx = null;
SessionFactory sessionFactory = Util.getSF();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try  {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS "  +"User"+
                            "(id bigint not null auto_increment, age tinyint, lastName varchar(255), name varchar(255), " +
                            "primary key (id))")
                    .executeUpdate();
            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != tx) {
                tx.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try  {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS "+"User");
            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != tx) {
                tx.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            User user = new User();

            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);


            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != tx) {
                tx.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (null != user) {
                session.delete(user);
            }
            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != tx) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from User").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE "+"User")
                    .executeUpdate();
            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != tx) {
                tx.rollback();
            }
        }
    }
}
