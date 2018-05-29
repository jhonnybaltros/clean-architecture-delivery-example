package com.delivery.data.db.jpa.repositories;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;
import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CousineRepositoryImpTest {

    @InjectMocks
    private CousineRepositoryImp cousineRepository;

    @Mock
    private JpaCousineRepository jpaCousineRepository;

    @Test
    public void getStoresByIdentityReturnsStores() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = TestCoreEntityGenerator.randomIdentity();

        StoreData storeData = StoreData.fromStore(store);
        Set<StoreData> stores = new HashSet<>();
        stores.add(storeData);

        // and
        doReturn(stores)
                .when(jpaCousineRepository)
                .findStoresById(id.getNumber());

        // when
        final Set<Store> actual = cousineRepository.getStoresByIdentity(id);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void getAllReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineData cousineData = CousineData.fromCousine(cousine);

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findAll();
        // when
        final List<Cousine> actual = cousineRepository.getAll();

        // then
        assertThat(actual).containsOnly(cousine);
    }

    @Test
    public void searchCousineByNameReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineData cousineData = CousineData.fromCousine(cousine);
        String search = "abc";

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findByNameContainingIgnoreCase(search);

        // when
        final List<Cousine> actual = cousineRepository.searchByName(search);

        // then
        assertThat(actual).isEqualTo(Collections.singletonList(cousine));
    }
}