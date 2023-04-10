import StatsManageSide from "@views.admin/side/StatsManageSide";
import StatsByDate from "@views.admin/stats/StatsByDate";
import StatsByAge from "@views.admin/stats/StatsByAge";
import StatsByCart from "@views.admin/stats/StatsByCart";
import StatsBySalesDate from "@views.admin/stats/StatsBySalesDate";
import StatsBySaleProductRank from "@views.admin/stats/StatsBySaleProductRank.vue";
import StatsBySaleCateRank from "@views.admin/stats/StatsBySaleCateRank";
import StatsByClaimRank from "@views.admin/stats/StatsByClaimRank";
import StatsByNewMember from "@views.admin/stats/StatsByNewMember";

const route = {
    path: 'stats',
    name: 'admin.stats',
    component: StatsManageSide,
    children: [
        {
            path: 'date',
            name: 'admin.stats.date',
            component: StatsByDate,
        },
        {
            path: 'age',
            name: 'admin.stats.age',
            component: StatsByAge
        },
        {
            path: 'cart',
            name: 'admin.stats.cart',
            component: StatsByCart
        },
        {
            path: 'saledate',
            name: 'admin.stats.saledate',
            component: StatsBySalesDate
        },
        {
            path: 'salerank',
            name: 'admin.stats.salerank',
            component: StatsBySaleProductRank
        },
        {
            path: 'salecaterank',
            name: 'admin.stats.salecaterank',
            component: StatsBySaleCateRank
        },
        {
            path: 'claim',
            name: 'admin.stats.claim',
            component: StatsByClaimRank
        },
        {
            path: 'newmember',
            name: 'admin.stats.newmember',
            component: StatsByNewMember
        }
    ]
}

export default route;