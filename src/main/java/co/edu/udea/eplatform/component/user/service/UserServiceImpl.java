package co.edu.udea.eplatform.component.user.service;

import co.edu.udea.eplatform.component.user.model.User;
import co.edu.udea.eplatform.component.user.service.model.UserQuerySearchCmd;
import co.edu.udea.eplatform.component.user.service.model.UserSaveCmd;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserGateway userGateway;


    @Override
    public User create(@NotNull UserSaveCmd userToCreateCmd) {
        logger.debug("Begin create: userToCreateCmd = {}", userToCreateCmd);

        User userToCreate = UserSaveCmd.toModel(userToCreateCmd);

        userToCreate.setPassword(userToCreateCmd.getPassword() + "hash");

        User userCreated = userGateway.save(userToCreate);

        logger.debug("End create: userCreated = {}", userCreated);

        return userCreated;
    }


    @Override
    @Transactional(readOnly = true)
    public User findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        final User userFound = userGateway.findById(id);

        logger.debug("End findById: userFound = {}", userFound);
        return userFound;
    }

    @Override
    public User update(@NotNull Long id, @NotNull UserSaveCmd userToUpdateCmd) {
        logger.debug("Begin update: id = {}, userToUpdateCmd = {}", id, userToUpdateCmd);

        final User userInDateBase = findById(id);

        final User userToUpdate = userInDateBase.toBuilder().id(id)
                .names(userToUpdateCmd.getNames())
                .lastNames(userToUpdateCmd.getLastNames())
                .username(userToUpdateCmd.getUsername())
                .primaryEmailAddress(userToUpdateCmd.getPrimaryEmailAddress())
                .password(userToUpdateCmd.getPassword())
                .primaryPhoneNumber(userToUpdateCmd.getPrimaryPhoneNumber())
                .build();

        final User userUpdated = userGateway.update(userToUpdate);

        logger.debug("End update: userUpdated = {}", userUpdated);
        return userUpdated;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin deleteById = {}", id);

        userGateway.deleteById(id);

        logger.debug("End findById = {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteriaCmd, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteriaCmd = {}, pageable = {}", queryCriteriaCmd, pageable);

        Page<User> usersFound = userGateway.findByParameters(queryCriteriaCmd, pageable);

        logger.debug("End findByParameters: usersFound = {}", usersFound);
        return usersFound;
    }

}
