package com.example.demo.service;

import com.example.demo.constants.OrderStatus;
import com.example.demo.constants.UserRole;
import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.ApiExceptionResponse;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for authentication-related operations.
 */
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Constructs an instance of AuthServiceImpl with userRepository and orderRepository.
     *
     * @param userRepository  Repository for User entities.
     * @param orderRepository Repository for Order entities.
     */


    public AuthServiceImpl(UserRepository userRepository,OrderRepository orderRepository){
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new user with the provided signup details.
     *
     * @param signupRequest Signup request containing user details.
     * @return DTO representing the created user.
     */

    public UserDto createUser(SignupRequestDto signupRequest){
        // Create a new user entity
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        // Save the user entity
        User createUser = userRepository.save(user);

        // Create an order for the new user
        Order order = new Order();
        order.setAmount(0D);
        order.setTotalAmount(0D);
        order.setDiscount(0L);
        order.setUser(createUser);
        order.setOrderStatus(OrderStatus.Pending);

        orderRepository.save(order);

        // Return DTO representing the created user
        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        return userDto;
    }

    /**
     * Saves the given user entity.
     *
     * @param user User entity to save.
     * @return Saved user entity.
     */

    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email Email to check for existence.
     * @return True if a user with the given email exists, false otherwise.
     */

    public Boolean hasUserWithEmail(String email){
        if(userRepository.findByEmail(email)!=null){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Initializes the system with an admin user if one does not exist.
     */
    @PostConstruct
    public void createAdmin(){

        User admin = userRepository.findByRole(UserRole.ADMIN);
        if(null == admin){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }



    /**
     * Deletes a user with the given ID.
     *
     * @param id ID of the user to delete.
     * @return True if the user was deleted successfully, false otherwise.
     */

    public boolean deleteUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return List of DTOs representing all users.
     */

    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(User::getDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId ID of the user to retrieve.
     * @return DTO representing the user with the given ID, or null if not found.
     */

    public UserDto getUserById(Long userId){

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            return optionalUser.get().getDto();
        } else{
            return null;
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email Email of the user to retrieve.
     * @return User entity corresponding to the given email.
     */
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    /**
     * Retrieves a user by their name.
     *
     * @param name Name of the user to retrieve.
     * @return DTO representing the user with the given name, or null if not found.
     */
    public UserDto getUserByName(String name){

        Optional<User> optionalUser = userRepository.findByName(name);
        if(optionalUser.isPresent()){
            return optionalUser.get().getDto();
        } else{
            return null;
        }
    }

    /**
     * Updates a user's details.
     *
     * @param userId  ID of the user to update.
     * @param userDto DTO containing updated user details.
     * @return DTO representing the updated user.
     * @throws IOException if an I/O error occurs while updating.
     */

    public UserDto updateUser(Long userId, UserDto userDto) throws IOException {

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent() ){
            User user = optionalUser.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());
            return userRepository.save(user).getDto();
        }else{
            return null;
        }

    }

    /**
     * Retrieves a user by their email and password, used for login authentication.
     *
     * @param dto Authentication request containing email and password.
     * @return User entity if authentication is successful.
     * @throws ApiExceptionResponse if authentication fails.
     */

    public User login(AuthenticationRequestDto dto) throws ApiExceptionResponse {
        User user = userRepository.findFirstByEmailAndPassword(dto.getUsername(), dto.getPassword());
        if (user == null) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Username " + dto.getUsername() + " might not exist");
            errors.add("Username and password might not match");

            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return user;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id ID of the user to retrieve.
     * @return User entity corresponding to the given ID.
     */

    public User findUserById(Long id) {

        return userRepository.findById(id).orElseThrow();
    }


    /**
     * Retrieves a user by their name.
     *
     * @param name Name of the user to retrieve.
     * @return User entity corresponding to the given name.
     */
    public User findUserByName(String name) {

        return userRepository.findByName(name).orElseThrow();
    }
}
