package co.edu.udea.eplatform.component.user.service;

import co.edu.udea.eplatform.component.user.model.User;
import co.edu.udea.eplatform.component.user.service.model.UserQuerySearchCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface UserGateway {

    User save(@NotNull User userToCreate);

    User findById(@NotNull Long id);

    User update(@NotNull User userToUpdate);

    void deleteById(@NotNull Long id);

    Page<User> findByParameters(UserQuerySearchCmd queryCriteria, Pageable pageable);
}
