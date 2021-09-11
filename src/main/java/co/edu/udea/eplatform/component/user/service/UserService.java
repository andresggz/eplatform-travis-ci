package co.edu.udea.eplatform.component.user.service;

import co.edu.udea.eplatform.component.user.service.model.UserQuerySearchCmd;
import co.edu.udea.eplatform.component.user.service.model.UserSaveCmd;
import co.edu.udea.eplatform.component.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface UserService {

    User create(@NotNull UserSaveCmd userToCreateCmd);

    User findById(@NotNull Long id);

    User update(@NotNull Long id, @NotNull UserSaveCmd userToUpdateCmd);

    void deleteById(@NotNull Long id);

    Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteriaCmd, @NotNull Pageable pageable);
}
