import { ScreenDto } from "@/dto/ScreenDto";

const screenConstant: { [key: string]: ScreenDto } = {
    home: new ScreenDto("A000", "Home", "/home", true, () => import("../pages/HomeMenu.vue")),
    shopMenu: new ScreenDto("A100", "Store", undefined, true, undefined,
        [
            new ScreenDto("A110", "Store list", "/store/list", true, () => import("../pages/StoreMenu.vue")),
            new ScreenDto("A120", "Add store", "/store/add", true, () => import("../pages/StoreAdd.vue"))
        ]
    ),
    shopDetail: new ScreenDto("A130", "Shop detail", "/store/detail/:id?", false, () => import("../pages/StoreDetail.vue")),
    voucherMenu: new ScreenDto("A200", "Voucher list", "/voucher", true, () => import("../App.vue")),
    analyticMenu: new ScreenDto("A300", "Analytics", "/analytic", true, () => import("../pages/HomeMenu.vue")),
    aboutUsMenu: new ScreenDto("A400", "About us", "/about-us", true, () => import("../pages/AboutUsMenu.vue"))
}

export default screenConstant;
