package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying;

import com.tsconsulting.businessManage.application.domain.model.Personel;
import com.tsconsulting.businessManage.application.domain.ports.out.SessionAuthenticate;

import java.util.Optional;

public class SessionMemory implements SessionAuthenticate {

    Personel personel;

    @Override
    public void authenticate(Personel personel) {
        this.personel = personel;
    }

    @Override
    public Optional<Personel> currentUser() {
        return Optional.of(personel);
    }
}
