package br.com.ifsales.dao.mock;

import br.com.ifsales.model.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreMockDao {

    public Optional<Store> getStoreById(Long storeId) {
        Store store = new Store();
        store.setId(storeId);
        store.setName("MockStoreName");
        store.setCnjp("00.000.000/0000-00");
        store.setRegion("MockRegion");
        store.setAddress("MockAddress");
        store.setPhone("(11) 1234-5678");

        return Optional.of(store);
    }

    public List<Store> getAllStores() {
        List<Store> stores = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Store store = new Store();
            store.setId(i);
            store.setName("MockStoreName" + i);
            store.setCnjp("00.000.000/0000-" + String.format("%02d", i));
            store.setRegion("MockRegion" + i);
            store.setAddress("MockAddress" + i);
            store.setPhone("(11) 1234-567" + i);

            stores.add(store);
        }

        return stores;
    }
}