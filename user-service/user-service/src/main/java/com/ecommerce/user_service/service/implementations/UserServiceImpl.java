package com.ecommerce.user_service.service.implementations;

import com.ecommerce.user_service.Entity.Address;
import com.ecommerce.user_service.Entity.User;
import com.ecommerce.user_service.exception.custom_exception.AlreadyExistsException;
import com.ecommerce.user_service.exception.custom_exception.InvalidCredentials;
import com.ecommerce.user_service.exception.custom_exception.UserNotFound;
import com.ecommerce.user_service.repository.AddressRepository;
import com.ecommerce.user_service.repository.UserRepository;
import com.ecommerce.user_service.request.AddressRequest;
import com.ecommerce.user_service.request.LoginRequest;
import com.ecommerce.user_service.request.SignUpRequest;
import com.ecommerce.user_service.response.ApiResponse;
import com.ecommerce.user_service.response.LoginResponse;
import com.ecommerce.user_service.response.UserDto;
import com.ecommerce.user_service.service.JwtService;
import com.ecommerce.user_service.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserServiceImpl implements UserService {

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public LoginResponse signUp(SignUpRequest signUpRequest) {
        User user= userRepository.findByEmail(signUpRequest.getEmail());
        if(user!=null){
            logger.error("User already exists");
            throw new AlreadyExistsException("User already exists");
        }
        logger.warn("Creating User : {}",signUpRequest.getEmail());
        String pwd=encoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(pwd);
        User user1=modelMapper.map(signUpRequest,User.class);
        userRepository.save(user1);
        logger.info("User Created successfully");

        Long userId=userRepository.findIdByEmail(signUpRequest.getEmail());
        UserDto userDto=modelMapper.map(user1,UserDto.class);
        String jwtToken= jwtService.generateJwtToken(user1.getEmail(),user1.getId());
        LoginResponse loginResponse=new LoginResponse();
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setUserDto(userDto);
        logger.info("User logged in successfully");
        return loginResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user= userRepository.findByEmail(loginRequest.getEmail());
        if(user==null){
            logger.error("User does not exist");
            throw new UserNotFound("User not found");
        }
        logger.debug("Checking Credentials of user : {}",user.getEmail());
        if(!encoder.matches(loginRequest.getPassword(),user.getPassword())){
            logger.error("Invalid Password");
            throw new InvalidCredentials("Invalid Password");
        }
        UserDto userDto=modelMapper.map(user,UserDto.class);
        String jwtToken= jwtService.generateJwtToken(user.getEmail(),user.getId());
        LoginResponse loginResponse=new LoginResponse();
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setUserDto(userDto);
        logger.info("User logged in successfully");
        return loginResponse;
    }

    @Override
    public ApiResponse addAddress(AddressRequest addressRequest,Long userId) {
            User user=userRepository.findById(userId)
                    .orElseThrow(()-> new UserNotFound("User not found.. Please SignUp first"));
                Address address=modelMapper.map(addressRequest, Address.class);
                user.setAddress(address);
                userRepository.save(user);
        return new ApiResponse("Address added successfully");
    }

//    @Override
//    public ApiResponse updateAddress(AddressRequest addressRequest, Long userId,Long addressId) {
//        Optional<Address> address=addressRepository.findById(addressId);
//        if(address) {
//            throw new UserNotFound("Address not found");
//        }
//
//        return new ApiResponse("Address updated successfully");
//    }


}
