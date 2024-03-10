package by.bsuir.beerCompany.services.impl;

import by.bsuir.beerCompany.dao.AuthenticationRequest;
import by.bsuir.beerCompany.dao.AuthenticationResponse;
import by.bsuir.beerCompany.dao.NewUserDao;
import by.bsuir.beerCompany.dao.RegistrationRequest;
import by.bsuir.beerCompany.entity.Person;
import by.bsuir.beerCompany.entity.Roles;
import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.repo.PersonRepository;
import by.bsuir.beerCompany.repo.RolesRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import by.bsuir.beerCompany.security.MyUserDetails;
import by.bsuir.beerCompany.services.AuthenticationService;
import by.bsuir.beerCompany.services.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public AuthenticationResponse register(NewUserDao newUser) {
        String login = newUser.getLogin();

        if (userRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("User with login: %s already exists".formatted(login));
        }

        Person person = new Person();
        person.setName(newUser.getName());
        person.setSurname(newUser.getSurname());
        person.setPhone(newUser.getPhone());
        person.setEmail(newUser.getEmail());
        personRepository.save(person);

        Roles role = rolesRepository.findByPosition("Пользователь").orElseThrow();
        Users user = new Users()
                .setLogin(login)
                .setPassword(passwordEncoder.encode(newUser.getPassword()))
//                .setName(registrationRequest.name())
                .setRoles(role)
                .setPerson(person);
        userRepository.save(user);
        String token = jwtService.generateToken(new MyUserDetails(user));
        return new AuthenticationResponse(token);
    }

    @Override
//    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.login(),
                        authenticationRequest.password()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.login());
        String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }
}