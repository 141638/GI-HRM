<script lang="ts">
import { RouterView } from 'vue-router'
import Menubar from 'primevue/menubar';
import Avatar from 'primevue/avatar';
import screenConstant from './router/ScreenConstant';
import type { MenuItem } from 'primevue/menuitem';
import { defineComponent, ref, type Ref } from 'vue';
import type { ScreenDto } from './dto/ScreenDto';
import { useIsMobile } from './composables/WindowComposable';

export default defineComponent({
  name: "A000MainRouter",
  components: {
    Menubar,
    Avatar,
    RouterView
  },
  setup: () => {
    // check mobile screen
    const isMobile = useIsMobile();

    // read and init the menu's childrens
    const menuChildren = (childrens: ScreenDto[] | undefined) => {
      let arr: MenuItem[] = [];
      if (childrens && childrens.length > 0) {
        childrens.forEach(async child => {
          arr.push({
            key: child.getScreenCd,
            label: child.getScreenName,
            url: child.getPath,
            items: menuChildren(child.getChildren)
          })
        });

        return arr;
      }
      return undefined;
    }

    const menuItems: Ref<MenuItem[]> = ref(Object.values(screenConstant).filter(srcConst => srcConst.getAsMenu).map(scrConst => {
      return {
        key: scrConst.getScreenCd,
        label: scrConst.getScreenName,
        url: scrConst.getPath,
        items: menuChildren(scrConst.getChildren)
      };
    }));

    return {
      isMobile,
      menuItems
    };
  }
})

</script>

<template>
  <header>
    <div class="flex flex-row w-full align-items-center">
      <Menubar :model="menuItems" class="py-1 px-3 lg:justify-content-start justify-content-between">
        <template #start>
          <div class="flex flex-row align-items-center">
            <img alt="Vue logo" class="logo" src="@/assets/logo.svg" />
            <label class="h1 green">VUE</label>
            <label class="h1" style="color: #34495E;">JS</label>
          </div>
        </template>
        <template #item="{ item, props, hasSubmenu, root }">
          <a v-ripple class="flex align-items-center" v-bind="props.action" v-bind:href="item.url">
            <span :class="item.icon"></span>
            <span class="ml-2">{{ item.label }}</span>
            <Badge v-if="item.badge" :class="{ 'ml-auto': !root, 'ml-2': root }" :value="item.badge" />
            <span v-if="item.shortcut" class="ml-auto border-1 surface-border border-round surface-100 text-base p-1">{{
              item.shortcut }}</span>
            <i v-if="hasSubmenu"
              :class="['pi pi-angle-down', { 'pi-angle-down ml-2': root, 'pi-angle-right ml-auto': !root }]"></i>
          </a>
        </template>
        <template v-if="!isMobile" #end>
          <div class="grid w-full justify-content-end">
            <div class="pr-3" style="width: fit-content">
              <Avatar icon="pi pi-user" size="large" shape="circle" />
            </div>
            <div class="text-left" style="width: fit-content;">
              <label class="font-semibold text-sm">Pablo Picassio</label>
              <br />
              <label class="text-sm">Artist</label>
            </div>
          </div>
        </template>
      </Menubar>
    </div>
  </header>
  <RouterView />
</template>

<style scoped lang="scss">
header {
  line-height: 1.5;
  width: 100%;
  height: fit-content;
  padding-top: 1.25rem;
  padding-bottom: 0.2rem;
  margin-bottom: 1.5rem;
}

.logo-wrapper {
  display: flex;
  place-items: center;
}

.logo {
  display: block;
  width: 2rem;
  height: 2rem;
  margin: 0 auto 2rem;
  margin: 0 0.25rem 0 0;
}

.h1 {
  font-weight: 500;
  font-size: 2rem;
  position: relative;
}

:deep(.p-menubar) {
  border: none;
  padding: 0;
  width: 100% !important;
  height: 100% !important;

  li {
    padding: 0 1rem;

    a:hover {
      background-color: inherit;
    }
  }

  .p-menubar-start {
    width: 15% !important;
  }

  .p-menubar-root-list {
    width: 65% !important;
  }

  .p-menubar-end {
    width: 20% !important;
  }
}

:deep(.p-menubar.p-menubar-mobile-active .p-menubar-root-list) {
  left: 35% !important;
}

:deep(.p-menuitem-link)[aria-expanded="true"] {
  background-color: #f5f5f5;
}
</style>
