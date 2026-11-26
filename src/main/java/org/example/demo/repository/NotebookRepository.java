package org.example.demo.repository;

import org.example.demo.entity.Notebook;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {

    /** Case-insensitive, because the data contains both "ASUS" and "Asus". */
    List<Notebook> findByManufacturerIgnoreCase(String manufacturer, Sort sort);

    interface ChartPoint {
        String getLabel();
        Long getTotal();
    }

    @Query("select upper(n.manufacturer) as label, count(n) as total " +
            "from Notebook n group by upper(n.manufacturer) order by count(n) desc")
    List<ChartPoint> countByManufacturer();

    @Query("select o.osname as label, sum(n.pieces) as total " +
            "from Notebook n join n.opsystem o group by o.osname order by sum(n.pieces) desc")
    List<ChartPoint> stockByOpSystem();
}