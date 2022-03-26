package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUser(String model, int series) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Car where model = :model ");
        query.setParameter("model", model);
        List<Car> carList = (List<Car>) ((org.hibernate.query.Query<?>) query).list();
        Car car = null;
        User user = null;
        for (Car cars : carList) {
            if (cars.getModel().equals(model) && cars.getSeries() == series) {
                car = cars;
            }
        }

        if (car != null) {
            user = car.getUser();
        }
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

}
