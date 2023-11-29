package daw.project.apachas.repository;

import daw.project.apachas.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RGroup")
public interface RGroup extends CrudRepository<Group, Long>, PagingAndSortingRepository<Group, Long> {

    Group findByGroupId(long groupId);
}
