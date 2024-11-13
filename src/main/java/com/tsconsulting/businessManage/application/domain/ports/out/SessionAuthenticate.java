package com.tsconsulting.businessManage.application.domain.ports.out;

import com.tsconsulting.businessManage.application.domain.model.Personel;

import java.util.Optional;

public interface SessionAuthenticate {
    void authenticate(Personel personel);

    Optional<Personel> currentUser();
}
