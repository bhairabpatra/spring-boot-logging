package springbootlogging.controller;

import springbootlogging.dto.CourseRequestDTO;
import springbootlogging.dto.CourseResponseDTO;
import springbootlogging.dto.ServiceResponse;
import springbootlogging.service.CourseService;
import springbootlogging.util.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Validated CourseRequestDTO courseRequestDTO) {

        log.info("CourseController:addCourse Request payload : {}", AppUtils.convertObjectToJson(courseRequestDTO));
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>();
        CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
        serviceResponse.setStatus(HttpStatus.OK);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController:addCourse response  : {}", "The log message will show that the request payload ");
        return serviceResponse;
    }

    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findALlCourse() {
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourses();
        return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOS);
    }

    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable Integer courseId) {
        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }

    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseUsingRequestParam(@RequestParam(required = false) Integer courseId) {
        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        log.info("CourseController:deleteCourse deleting a course with id {}", courseId);
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    //assignment
    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseRequestDTO courseRequestDTO) {
        log.info("CourseController:updateCourse Request payload : {} and {}", AppUtils.convertObjectToJson(courseRequestDTO), courseId);
        CourseResponseDTO courseResponseDTO = courseService.updateCourse(courseId, courseRequestDTO);
        ServiceResponse<CourseResponseDTO> response = new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
        log.info("CourseController:updateCourse response body : {}", AppUtils.convertObjectToJson(response));
        return response;
    }

    private void validateRequestPayload(CourseRequestDTO courseRequestDTO) {
        if (courseRequestDTO.getDuration() == null || courseRequestDTO.getDuration().isEmpty()) {
            throw new RuntimeException("duration field must need to be pass");
        }
        if (courseRequestDTO.getFees() == 0) {
            throw new RuntimeException("fees must be provided");
        }
    }

    @GetMapping("/log")
    public String loggingLevel() {
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");
        return "log printed in console";
    }

}
