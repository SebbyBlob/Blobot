package com.blob.discord.utilities;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RoleUtils {

    public boolean hasRole(Member member, ArrayList<String> roles, Guild guild) { return checkRoles(member, null, roles, guild); }
    public boolean hasRole(Member member, String role, Guild guild) { return checkRoles(member, role, null, guild); }

    private boolean checkRoles(Member member, @Nullable String roleName, @Nullable ArrayList<String> roleList, Guild guild) {
        //Gets all the roles the member has
        List<Role> memberRoleList = member.getRoles();
        if (roleName == null) {
            //List of Roles check
            for (String roleName1 : roleList) {
                List<Role> roleList1 = guild.getRolesByName(roleName1, false);
                Role roleToCheck = roleList1.get(roleList1.size() - 1);
                if (memberRoleList.contains(roleToCheck)) {
                    return true;
                }
            }
        } else {
            //Single Role check
            List<Role> roleList1 = guild.getRolesByName(roleName, false);
            //Makes sure the role to check is not null
            if (!(roleList1.get(0) == null)) {
                Role roleToCheck = roleList1.get(roleList1.size() - 1);
                if (memberRoleList.contains(roleToCheck)) return true;
            } else {
                Core.getLogger().error("There is no role \"" + roleName + "\", RoleUtils().checkRoles");
                return false;
            }
        }
        return false;
    }

}
