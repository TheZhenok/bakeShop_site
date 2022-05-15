package spring.Models;

import org.springframework.security.core.GrantedAuthority;

public enum PaymentMethod implements GrantedAuthority {
    CARD, CASH;

    @Override
    public String getAuthority() {
        return name();
    }
}
