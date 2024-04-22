<template>
    <suspense>
        <template #fallback>
            <div>Loading...</div>
        </template>
        <child-component />
    </suspense>
    <div class="overview">
        <h2>Store menu</h2>
        <label>view all the shops in the system</label>
    </div>
    <div class="body-wrapper">
        <div class="body-header grid justify-content-between align-items-center">
            <div class="text-left col-12 lg:col mr-0 lg:mr-3 p-0">
                <InputGroup>
                    <CommonButton :icon="'pi pi-search'" @click-event="onClickSearch"></CommonButton>
                    <InputText placeholder="Search store..."></InputText>
                </InputGroup>
            </div>
            <div id="body-header-btn"
                class="grid justify-content-between lg:justify-content-end align-items-center col-fixed p-0">
                <div class="mr-2">
                    <label> Status: </label>
                    <Dropdown v-model="selectedStatus" :options="storeStatuses" optionLabel="label" optionValue="value"
                        class="w-8rem border-0"></Dropdown>
                </div>
                <div class="mr-2">
                    <CommonButton
                        :icon="'bx ' + (optionLayout == LAYOUT.GRID ? 'bxs-grid-alt' : 'bx-grid-alt') + ' bx-md'"
                        :class="'p-button-p-0 mr-1' + (optionLayout == LAYOUT.TABLE ? ' p-button-outlined' : '')"
                        @click-event="onClickChangeLayout(LAYOUT.GRID)"></CommonButton>
                    <CommonButton :icon="'bx ' + (optionLayout == LAYOUT.TABLE ? 'bxs-grid' : 'bx-grid') + ' bx-md'"
                        :class="'p-button-p-0' + (optionLayout == LAYOUT.GRID ? ' p-button-outlined' : '')"
                        @click-event="onClickChangeLayout(LAYOUT.TABLE)"></CommonButton>
                </div>
                <div class="btn-add-store">
                    <router-link to="/store/add">
                        <CommonButton :label="'New Store'" :icon="'pi pi-plus'"></CommonButton>
                    </router-link>
                </div>
            </div>
        </div>
        <div v-if="optionLayout == LAYOUT.GRID" class="body-grid-layout pt-3">
            <div v-for="store of storeList" v-bind:key="store.name" class="body-grid-item">
                <Card class="p-3">
                    <template #header>
                        <router-link :to="'/store/detail/' + store.id">
                            <label class="grid-item-header">{{ store.name }}</label>
                        </router-link>
                    </template>
                    <template #content>
                        <p><label>About the store</label>: {{ store.description.substring(0, 275) + "..." }}</p>
                        <p><label>Open time</label>: {{ store.openTime + ' - ' + store.closeTime }} </p>
                        <p><label>Contact us</label>: {{ store.contactNumber }}</p>
                    </template>
                </Card>
            </div>
        </div>
        <div v-if="optionLayout == LAYOUT.TABLE" class="body-table-layout pt-3">
            <DataTable :value="storeList" :columns="columns">
                <Column v-for="col of columns" :key="col.field" :field="col.field" :header="col.header">
                </Column>
            </DataTable>
        </div>
    </div>
</template>
<script lang="ts">
import { computed, defineComponent, onMounted, ref, type Ref } from 'vue';
import { eventSource } from '@/utils/ApiUtils';
import InputText from 'primevue/inputtext';
import InputGroup from 'primevue/inputgroup';
import DataTable from 'primevue/datatable';
import type { StoreListDto } from '@/dto/StoreListDto';
import Column from 'primevue/column';
import type { ApiResponse } from '@/dto/ApiResponse';
import { ResponseStatusConstant } from '@/assets/Constants';
import Card from 'primevue/card';
import Dropdown from 'primevue/dropdown';
import CommonButton from '@/components/module/CommonButton.vue';

export default defineComponent({
    name: "A100StoreMenu",
    components: {
        CommonButton,
        InputText,
        InputGroup,
        DataTable,
        Column,
        Card,
        Dropdown
    },
    setup: () => {
        // table layout
        const LAYOUT = {
            GRID: 0,
            TABLE: 1
        }

        // user choice's layout
        let optionLayoutRef = ref(LAYOUT.GRID)
        const optionLayout = computed(() => optionLayoutRef.value);

        // on user changing layout style
        const onClickChangeLayout = async (layout: number) => {
            optionLayoutRef.value = layout;
        }

        // store table columns
        const columns = [
            { field: "name", header: "Name" },
            { field: "contactNumber", header: "Contact number" },
            { field: "emailAddress", header: "Email" },
            { field: "openTime", header: "Open" },
            { field: "closeTime", header: "Close" },
            { field: "status", header: "Status" },
        ]

        // store status options
        const storeStatuses = ref([
            { label: 'All', value: 0 },
            { label: 'Active', value: 1 },
            { label: 'Inactive', value: 2 },
        ]);
        let selectedStatus = ref(storeStatuses.value[0].value);

        // list of stores
        let storeList: Ref<StoreListDto[]> = ref([]);

        // on clicking the search button: call SSE for the store list
        const onClickSearch = async () => {
            let evs = eventSource('/vuejsdemo/store/list');
            evs.onopen = () => {
                storeList.value = [];
            }
            evs.onmessage = (event) => {
                const data = JSON.parse(event.data) as ApiResponse;
                if (data && data.status === ResponseStatusConstant.OK) {
                    storeList.value.push(data.item);
                } else {
                    console.log(event);
                }
            }
            evs.onerror = () => {
                evs.close();
            };
        }

        // initialize functions that will run on mounted
        const init = async () => {
            await onClickSearch();
        }
        onMounted(async () => {
            await init();
        });

        return {
            LAYOUT,
            optionLayout,
            onClickChangeLayout,
            storeStatuses,
            selectedStatus,
            columns,
            storeList,
            onClickSearch
        };
    }
})
</script>
<style scoped lang="scss">
@media (max-width:760px) {
    .body-grid-layout {
        .body-grid-item {
            width: 100% !important;
        }
    }

    #body-header-btn {
        width: 100% !important;
        margin-top: 0.25rem !important;

        :deep(.btn-add-store) {
            .p-button-label {
                display: none !important;
            }

            .p-button {
                padding: 0.7rem 0.7rem !important;
            }
        }
    }
}

#body-header-btn :deep(.btn-add-store .p-button-label) {
    padding-left: 0.3rem;
}

.body-grid-layout {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;

    .body-grid-item {
        width: 49%;
        padding: 0.75rem 0rem;

        :deep(.p-card) {
            height: 100%;
        }

        p label {
            color: #6EE7B7;
        }

        .grid-item-header {
            font-size: 1.2rem;
            font-weight: 600;
            color: #6EE7B7;
        }
    }
}
</style>