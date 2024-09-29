package kg.megacom.diplomaprojectschoolmegalab.controller;


import jakarta.persistence.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;


    @GetMapping(value = "/get-user-by-id/{id}")
    public ResponseEntity<Response> getStaffById(@PathVariable Long id) {
        log.info("[#getUserById] is calling");

        try {
            Response response = userService.getUserResponseById(id);
            return ResponseEntity.ok(response);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(e.getMessage(), null));
        }

    }
    @GetMapping(value = "/get-all-user")
    public ResponseEntity<Response> getAllUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "username,asc") String[] sort) {
        log.info("[#getAllUsers] is calling");
        Response response = userService.getAllUsersWithPagination(page, size, sort);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Response> updateEmployee(@RequestBody UserDto userDto, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response response = userService.updateUser( id,userDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable Long id) {
        log.info("[#delete] is calling");
        userService.delete(id);
        return ResponseEntity.ok(new Response("Deleted!", "ID: " + id));
    }
}
