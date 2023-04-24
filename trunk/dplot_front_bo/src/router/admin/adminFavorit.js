import FavoriteManageSide from "@views.admin/side/FavoriteMenuManageSide";
import AdminDashBoard from "@views.admin/AdminDashBoard";

const route = {
    path: '',
    name: 'admin.main',
    component: FavoriteManageSide,
    children: [
        {
            path: '/main',
            name: 'admin.main.dashboard',
            component: AdminDashBoard
        },
    ]
}

export default route;
