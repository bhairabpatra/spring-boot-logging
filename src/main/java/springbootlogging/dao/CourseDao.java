package springbootlogging.dao;

import springbootlogging.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<CourseEntity,Integer> {

}
