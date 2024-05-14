package dev.fastball.portal.jpa.ui;

import dev.fastball.core.annotation.*;
import dev.fastball.core.component.DataResult;
import dev.fastball.core.component.RecordActionFilter;
import dev.fastball.meta.basic.PopupType;
import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.portal.jpa.entity.JpaUserEntity;
import dev.fastball.portal.jpa.model.UserQueryModel;
import dev.fastball.portal.jpa.repo.UserRepo;
import dev.fastball.ui.components.table.SearchTable;
import dev.fastball.ui.components.table.param.TableSearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@UIComponent
@RequiredArgsConstructor
@ViewActions(
        actions = @ViewAction(key = "new", name = "新建", popup = @Popup(@RefComponent(UserForm.class))),
        recordActions = {
//                @ViewAction(key = "edit", name = "编辑", popup = @Popup(@RefComponent(UserForm.class))),
                @ViewAction(key = "role", name = "角色授权", popup = @Popup(value = @RefComponent(UserRoleForm.class), popupType = PopupType.Modal)),
                @ViewAction(key = "resetPassword", name = "重置密码", popup = @Popup(value = @RefComponent(ResetPasswordForm.class), popupType = PopupType.Modal, width = "25%"))
        }
)
public class UserTable implements SearchTable<JpaUserEntity, UserQueryModel> {

    private final UserRepo userRepo;

    private final FastballPortalService portalService;


    @Override
    public DataResult<JpaUserEntity> loadData(TableSearchParam<UserQueryModel> search) {
        PageRequest pageRequest = PageRequest.of(search.getCurrent().intValue() - 1, search.getPageSize().intValue());
        Page<JpaUserEntity> result;
        if (search.getSearch() == null) {
            result = userRepo.findAll(pageRequest);
        } else {
            Example<JpaUserEntity> example = Example.of(
                    JpaUserEntity.builder()
                            .username(search.getSearch().getUsername())
                            .nickname(search.getSearch().getNickname())
                            .build()
            );
            result = userRepo.findAll(example, pageRequest);
        }
        result.forEach(user -> user.setPassword(null));
        return DataResult.build(result.getTotalElements(), result.getContent());
    }

    @RecordAction(name = "启用", recordActionFilter = EnabledRecordActionFilter.class)
    public void enable(JpaUserEntity user) {
        portalService.enableUser(user.getId());
    }

    @RecordAction(name = "禁用", recordActionFilter = DisabledRecordActionFilter.class)
    public void disable(JpaUserEntity user) {
        portalService.disableUser(user.getId());
    }

    public static class EnabledRecordActionFilter implements RecordActionFilter<JpaUserEntity> {
        @Override
        public boolean filter(JpaUserEntity user) {
            return user.getStatus() != UserStatus.Enabled;
        }
    }

    public static class DisabledRecordActionFilter implements RecordActionFilter<JpaUserEntity> {
        @Override
        public boolean filter(JpaUserEntity user) {
            return user.getStatus() == UserStatus.Enabled;
        }
    }
}
