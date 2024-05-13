package dev.fastball.portal.jpa.ui;


import dev.fastball.core.annotation.*;
import dev.fastball.core.component.DataResult;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.jpa.entity.JpaRoleEntity;
import dev.fastball.portal.jpa.model.RoleDTO;
import dev.fastball.portal.jpa.model.RoleQueryModel;
import dev.fastball.portal.jpa.repo.RoleRepo;
import dev.fastball.ui.components.table.SearchTable;
import dev.fastball.ui.components.table.param.TableSearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@UIComponent
@RequiredArgsConstructor
@ViewActions(
        actions = @ViewAction(key = "new", name = "新建", popup = @Popup(@RefComponent(RoleForm.class))),
        recordActions = @ViewAction(key = "edit", name = "编辑", popup = @Popup(@RefComponent(RoleForm.class)))
)
public class RoleTable implements SearchTable<RoleDTO, RoleQueryModel> {

    private final RoleRepo roleRepo;


    @Override
    public DataResult<RoleDTO> loadData(TableSearchParam<RoleQueryModel> search) {
        PageRequest pageRequest = PageRequest.of(search.getCurrent().intValue() - 1, search.getPageSize().intValue());
        Page<JpaRoleEntity> result;
        if (search.getSearch() == null) {
            result = roleRepo.findAll(pageRequest);
        } else {
            Example<JpaRoleEntity> example = Example.of(
                    JpaRoleEntity.builder().name(search.getSearch().getName()).build()
            );
            result = roleRepo.findAll(example, pageRequest);
        }
        List<RoleDTO> roleDTOList = result.getContent().stream().map(roleEntity -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(roleEntity.getId());
            roleDTO.setCode(roleEntity.getCode());
            roleDTO.setName(roleEntity.getName());
            roleDTO.setDescription(roleEntity.getDescription());
            roleDTO.setPermissions(roleEntity.getPermissions().stream().map(Permission::getId).collect(Collectors.toList()));
            return roleDTO;
        }).collect(Collectors.toList());
        return DataResult.build(result.getTotalElements(), roleDTOList);
    }
}