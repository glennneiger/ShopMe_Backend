package com.intive.shopme.category;

import com.intive.shopme.model.db.DbCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CategoryRepository extends JpaRepository<DbCategory, String> {

    boolean existsByName(String name);
}
