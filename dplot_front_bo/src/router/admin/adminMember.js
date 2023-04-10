import MemberManageSide from "@views.admin/side/MemberManageSide";
import MemberTotalList from "@views.admin/member/AdminMemberTotalList";
import MemberDormancyList from "@views.admin/member/AdminMemberDormancyList";
import MemberResignList from "@views.admin/member/AdminMemberResignList";
import MemberManageLevel from "@views.admin/member/AdminMemberManageLevel";

const route = {
    path: 'member',
    name: 'admin.member',
    component: MemberManageSide,
    children: [
        {
            path: 'total/list',
            name: 'admin.member.total.list',
            component: MemberTotalList
        },
        {
            path: 'dormancy/list',
            name: 'admin.member.dormancy.list',
            component: MemberDormancyList
        },
        {
            path: 'resign/list',
            name: 'admin.member.resign.list',
            component: MemberResignList
        },
        {
            path: 'manage/level',
            name: 'admin.member.manage.level',
            component: MemberManageLevel
        },
    ]
}

export default route;