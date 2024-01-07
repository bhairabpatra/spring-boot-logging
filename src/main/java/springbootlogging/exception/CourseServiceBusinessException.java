package springbootlogging.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseServiceBusinessException extends RuntimeException{
    public CourseServiceBusinessException(String message) {
        super(message);
    }
}
