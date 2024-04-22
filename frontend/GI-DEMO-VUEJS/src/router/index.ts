import { createRouter, createWebHistory, type RouteComponent, type RouteRecordRaw } from 'vue-router'
import type { ScreenDto } from '@/dto/ScreenDto'
import screenConstants from "./ScreenConstant";

const editRouter = (pages: ScreenDto[]) => {
  const children: {
    path: string;
    props: boolean;
    name: string;
    component: RouteComponent;
    children?: RouteRecordRaw[];
  }[] = [];
  pages.forEach((page: ScreenDto) => {
    if (page.getModuleImport) {
      children.push({
        path: page.getPath,
        props: true,
        name: page.getScreenName,
        component: page.getModuleImport,
        children: page.getChildren ? editRouter(Object.values(page.getChildren)) : undefined
      });
    } else {
      if (page.getChildren) {
        children.push(...Object.values(editRouter(Object.values(page.getChildren))));
      }
    }
  });
  return children;
}

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    children: editRouter(Object.values(screenConstants))
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// router.beforeEach((to, from, next) => {
// TokenUtils.refreshToken().then(() => {
//   next();
// });
// });

export default router
