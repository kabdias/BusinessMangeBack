package com.tsconsulting.businessManage.application.domain.ports.out;

import com.tsconsulting.businessManage.application.domain.model.Personel;

import java.util.Optional;
import java.util.Set;

public interface PersonelRepository {
    void add(Personel personel);

    Set<Personel> getAll();

}
