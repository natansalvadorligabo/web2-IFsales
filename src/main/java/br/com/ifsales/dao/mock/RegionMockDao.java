package br.com.ifsales.dao.mock;

import br.com.ifsales.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegionMockDao {
    public Optional<Region> getRegionById(Long regionId) {
        Region region = new Region();
        region.setId(regionId);
        region.setName("MockRegionName");
        region.setCity("MockCity");
        region.setState("MockState");

        return Optional.of(region);
    }

    public List<Region> getAllRegions() {
        List<Region> regions = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Region region = new Region();
            region.setId(i);
            region.setName("MockRegionName" + i);
            region.setCity("MockCity" + i);
            region.setState("MockState" + i);

            regions.add(region);
        }

        return regions;
    }
}