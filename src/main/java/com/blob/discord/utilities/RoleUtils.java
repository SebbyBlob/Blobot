package com.blob.discord.utilities;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RoleUtils {

    public boolean hasRole(Long userID, ArrayList roles) { return checkRoles(userID, null, roles); }
    public boolean hasRole(Long userID, String role) { return checkRoles(userID, role, null); }

    private boolean checkRoles(Long userID, @Nullable String roleName, @Nullable ArrayList roleList) {
        List<Role> roles = Core.getJDA().getGuildById("530904600119869450").retrieveMemberById(userID).complete().getRoles();
        if (roleName == null) {
            for (Role role : roles) {
                if (roleList.contains(role.getName())) {
                    return true;
                }
            }
            return false;
        } else {
            for (Role role : roles) {
                if (role.getName().equals(roleName)) {
                    return true;
                }
            }
            return false;
        }
    }

}
