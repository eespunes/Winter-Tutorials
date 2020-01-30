package cat.tecnocampus.omega.security;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    private UserDetails findByUsername(String username) {
        final String USERS_QUERY = "SELECT username, password, enable FROM Users WHERE username = ?";
        final String ROLES_QUERY = "SELECT username, role FROM Roles WHERE username = ?";

        RowMapper<User.UserBuilder> mapper = (resultSet, i) -> {
            User.UserBuilder builder = null;
            builder = User.withUsername(resultSet.getString("username"));
            builder.password(resultSet.getString("password"));
            return builder;
        };

        try {
            List<GrantedAuthority> roles = jdbcTemplate.query(ROLES_QUERY, new Object[]{username},
                    (resultSet, i) -> new SimpleGrantedAuthority(resultSet.getString("role")));

            User.UserBuilder builder = jdbcTemplate.queryForObject(USERS_QUERY, new Object[]{username}, mapper);

            builder.authorities(roles);
            return builder.build();
        }
        catch (IncorrectResultSizeDataAccessException ex) {
            throw new UsernameNotFoundException("this user does not exist");
        }
    }
}
