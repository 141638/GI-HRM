<template>
    <div class="overview">
        <h2>Home Dashboard</h2>
        <label>checkout real quick some overview right here</label>
    </div>
    <div class="grid align-items-baseline">
        <div class=" grid col-7">
            <div class="grid w-full">
                <span class="col-6">
                    <Card>
                        <template #content>
                            <div class="card-content">
                                <div>
                                    <label class="card-header">Total Product Viewed</label> <br />
                                    <label class="card-desc">customer has viewd product</label>
                                </div>
                                <p class="card-value">421.987</p>
                            </div>
                        </template>
                    </Card>
                </span>
                <span class="col-6">
                    <Card class="h-full">
                        <template #content>
                            <div class="card-content">
                                <div>
                                    <label class="card-header">Total Sales</label> <br />
                                    <label class="card-desc">product have been saled</label>
                                </div>
                                <p class="card-value">$28.912</p>
                            </div>
                        </template>
                    </Card>
                </span>
            </div>
            <div class="col-12">
                <DataTable :value="products" :columns="columns" tableStyle="min-width: 50rem">
                    <Column v-for="col of columns" :key="col.field" :field="col.field" :header="col.header"></Column>
                </DataTable>
            </div>
        </div>
        <div class="col-5">
            <div class="col-12">
                <Card>
                    <template #content>
                        <div class="card-content">
                            <div>
                                <label class="card-header">Balance</label> <br />
                                <label class="card-desc">amount of income and results</label>
                            </div>
                            <p class="card-value">$125.235</p>
                            <Chart type="bar" :data="chartData" />
                        </div>
                    </template>
                </Card>
            </div>
            <div class="col-12">
                <Card>
                    <template #content>
                        <div class="card-content">
                            <div>
                                <label class="card-header">Audience</label> <br />
                                <label class="card-desc">customer have visited website</label>
                            </div>
                            <Chart type="bar" :data="chartData" :options="chartOptions" />
                        </div>
                    </template>
                </Card>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import Card from 'primevue/card';
import Chart from 'primevue/chart';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';

const chartData = {
    labels: ['January', 'February', 'March', 'April', 'May'],
    datasets: [
        {
            label: 'Revenue',
            backgroundColor: '#42A5F5',
            data: [50, 25, 75, 60, 90]
        },
        {
            label: 'Profit',
            backgroundColor: '#FFA726',
            data: [30, 20, 55, 40, 70]
        }
    ]
};

const products = [
    { id: 1, name: 'Laptop', category: 'Electronics', price: 999.99, quantity: 35 },
    { id: 2, name: 'Smartphone', category: 'Electronics', price: 599.99, quantity: 85 },
    { id: 3, name: 'Watch', category: 'Accessories', price: 199.99, quantity: 60 },
    { id: 4, name: 'Headphones', category: 'Electronics', price: 129.99, quantity: 75 },
    { id: 5, name: 'Camera', category: 'Electronics', price: 499.99, quantity: 50 },
    { id: 6, name: 'Shoes', category: 'Fashion', price: 149.99, quantity: 90 },
    { id: 7, name: 'Backpack', category: 'Fashion', price: 69.99, quantity: 120 },
    { id: 8, name: 'Sunglasses', category: 'Accessories', price: 89.99, quantity: 150 }
];
const chartOptions = {
    indexAxis: 'y',
    maintainAspectRatio: false,
    aspectRatio: 0.8,
    scales: {
        x: {
            ticks: {
                font: {
                    weight: 500
                }
            },
            grid: {
                display: false,
                drawBorder: false
            }
        },
        y: {
            grid: {
                drawBorder: false
            }
        }
    }
};

const columns = [
    { field: "name", header: "Name" },
    { field: "category", header: "Category" },
    { field: "price", header: "Price" },
    { field: "quantity", header: "Quantity" }
]

</script>

<style scoped lang="scss">
.card-content {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.card-header {
    font-weight: 600;
    font-size: 1.25rem;
}

.card-desc {
    font-size: 0.85rem;
}

.card-value {
    font-weight: 600;
    font-size: 2rem;
    color: #41B883;
}

.grid {
    padding: 0;
    margin: 0;
}

:deep(.p-card .p-card-body, ) {
    height: 100% !important;
    border: 1px rgb(91 91 91 / 12%) solid !important;
    border-radius: 1rem;
}

:deep(.p-datatable) {
    border: 1px rgb(91 91 91 / 12%) solid !important;
    border-radius: 1rem;
}
</style>