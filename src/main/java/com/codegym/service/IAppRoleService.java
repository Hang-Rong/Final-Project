package com.codegym.service;

import com.codegym.role.AppRole;

public interface IAppRoleService extends IGeneralService<AppRole>{
    AppRole findByName(String name);
}
