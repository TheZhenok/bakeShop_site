package spring.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.security.Principal;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "buyer")
public class Buyer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Date finishDate = getCreated();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Order order;

    @ElementCollection(targetClass = PaymentMethod.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "buyer_method", joinColumns = @JoinColumn(name = "buyer_id"))
    @Enumerated(EnumType.STRING)
    private Set<PaymentMethod> method;
}
