package com.example.gamestorespring.service;

import com.example.gamestorespring.entity.BasketEntity;
import com.example.gamestorespring.entity.RoleEntity;
import com.example.gamestorespring.entity.UserDetailsEntity;
import com.example.gamestorespring.entity.UserEntity;
import com.example.gamestorespring.exception.UserAlreadyExistException;
import com.example.gamestorespring.model.RoleModel;
import com.example.gamestorespring.model.UserModel;
import com.example.gamestorespring.repository.BasketRepo;
import com.example.gamestorespring.repository.RoleRepo;
import com.example.gamestorespring.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private BasketRepo basketRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RoleRepo roleRepo, UserRepo userRepo, BasketRepo basketRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.basketRepo = basketRepo;
    }

    public Set<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepo.findAll();
    }

    public String create(UserModel user) throws UserAlreadyExistException {
        UserEntity new_user = new UserEntity();
        if (userRepo.findByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        }

        new_user.setLogin(user.getLogin());
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
        new_user.getRoles().add(user.getRole());

        UserDetailsEntity userDetails = new UserDetailsEntity();
        userDetails.setName(user.getName());
        userDetails.setSurname(user.getSurname());
        userDetails.setEmail(user.getEmail());
        userDetails.setPhone(user.getPhone());
        new_user.setUserDetails(userDetails);

        new_user = userRepo.save(new_user);

        BasketEntity basket = new BasketEntity();
        basket.setUser(new_user);
        basketRepo.save(basket);

        return "Пользователь успешно добавлен";
    }

    public UserEntity regUser(UserModel user) throws Exception {
        if (userRepo.findByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        }
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            throw new Exception("Пароли не совпадают");
        }
        UserEntity new_user = new UserEntity();
        new_user.setLogin(user.getLogin());
        RoleEntity role = roleRepo.findByRole("ROLE_USER").get();
        new_user.getRoles().add(role);
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDetailsEntity details = new UserDetailsEntity();
        new_user.setUserDetails(details);

        new_user =  userRepo.save(new_user);

        BasketEntity basket = new BasketEntity();
        basket.setUser(new_user);
        basketRepo.save(basket);

        return new_user;
    }

    public UserModel getUserById(long id) {
        UserEntity userEntity = userRepo.findById(id).get();

        return convertUserEntityToUserModel(userEntity);
    }

    public UserModel changeUser(UserModel user, long id) {
        UserEntity changeUser = userRepo.findById(id).get();

        changeUser.setLogin(user.getLogin());
        changeUser.getUserDetails().setPhone(user.getPhone());
        changeUser.getUserDetails().setEmail(user.getEmail());
        changeUser.getUserDetails().setName(user.getName());
        changeUser.getUserDetails().setSurname(user.getSurname());
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(user.getRole());
        changeUser.setRoles(roles);

        UserEntity updateUser = userRepo.save(changeUser);

        return convertUserEntityToUserModel(updateUser);

    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public UserModel getUserByLogin(String login) {
        UserEntity userEntity = userRepo.findByLogin(login).get();

        return convertUserEntityToUserModel(userEntity);
    }

    private UserModel convertUserEntityToUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        UserDetailsEntity userDetails = userEntity.getUserDetails();

        userModel.setId(userEntity.getId());
        userModel.setLogin(userEntity.getLogin());
        for (RoleEntity role: userEntity.getRoles()){
            userModel.setRole(role);
        }
        userModel.setEmail(userDetails.getEmail());
        userModel.setName(userDetails.getName());
        userModel.setSurname(userDetails.getSurname());
        userModel.setPhone(userDetails.getPhone());

        return userModel;
    }
}
