package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying;

import com.tsconsulting.businessManage.application.domain.model.Personel;
import com.tsconsulting.businessManage.application.domain.ports.out.PersonelRepository;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashSet;
import java.util.Set;

public class PersonelMemory implements PersonelRepository {

    final Set<Personel> personels = new LinkedHashSet<>();

    private HttpSession httpSession;
    @Override
    public void add(Personel personel) {
       personels.add(personel);
    }

    @Override
    public Set<Personel> getAll() {
        return personels;
    }

}
