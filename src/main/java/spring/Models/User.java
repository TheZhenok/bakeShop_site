package spring.Models;

import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 20, message = "*Имя слишком мало или велико")
    private String name;

    @Size(min = 2, max = 20, message = "*Фамилия слишком мала или велика")
    private String lastname;

    @Size(min = 0, max = 20, message = "*Отчество слишком велико")
    private String fathername;

    @Size(min = 5, message = "*Логин слишком мал")
    private String username;

    @Size(min = 4, message = "*Пароль должен состоять из 6 или более символов")
    private String password;
    private String repeatPassword;
    private int pay;
    private boolean active;
    private String avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Buyer> buyers;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User(String name, String lastname, String fathername, String username, String password, String repeatPassword, int pay, boolean active, String avatar, Set<Role> roles) {
        this.name = name;
        this.lastname = lastname;
        this.fathername = fathername;
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.pay = pay;
        this.active = active;
        this.avatar = avatar;
        this.roles = roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean equalsUser() {
        return this.password.equals(this.repeatPassword) ;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(getRoles());
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public boolean isActive(){
        return active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean check(){

        if( (this.name.length() < 3 && this.name != null)
                || (this.lastname.length() < 3 && this.lastname != null)
                || (this.username.length() < 3 && this.username != null)){
            return false;
        }
        return true;
    }
}
