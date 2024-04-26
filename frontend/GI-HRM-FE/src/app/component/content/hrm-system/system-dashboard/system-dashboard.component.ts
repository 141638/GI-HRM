import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-system-dashboard',
  templateUrl: './system-dashboard.component.html',
  styleUrls: ['./system-dashboard.component.scss']
})
export class SystemDashboardComponent implements OnInit {
  public halfYearRevenueDataChart: any;
  public salaryData: any;
  public revenueProgressBarValue: any;
  public projectTableOverviews: any;
  constructor() {
  }
  ngOnInit() {
    this.setRevenueChart();
    this.setSalaryChart();
    this.setRevenueProgressBar();
    this.setProjectTableOverview();
  }

  setRevenueChart() {
    this.halfYearRevenueDataChart = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
          label: 'Income',
          data: [12, 30, 22.3, 30.125, 25, 38.5, 43.52],
          fill: false,
          borderColor: '#42A5F5',
          tension: .4
        },
        {
          label: 'Outcome',
          data: [20, 15, 20, 25, 30, 40, 50],
          fill: false,
          borderColor: '#FFA726',
          tension: .4
        }
      ]
    };
  }

  setSalaryChart() {
    this.salaryData = {
      labels: ['Onsite', 'Onboard', 'Remote'],
      datasets: [
        {
          data: [300, 50, 100],
          backgroundColor: [
            "#1E8424",
            "#36A2EB",
            "#FFCE56"
          ],
          hoverBackgroundColor: [
            "#1E8424",
            "#36A2EB",
            "#FFCE56"
          ]
        }
      ]
    };
  }

  setRevenueProgressBar() {
    this.revenueProgressBarValue = 87;
  }

  setProjectTableOverview() {
    this.projectTableOverviews = [
      {
        projectCode: 'HR0198',
        projectName: 'Human Resource Management Webapp',
        estimateDate: 'Dec 20, 2022 - Jun 20, 2023',
        technology: 'Java/Angular',
        leadTeam: 'Java Department',
        status: 'Ongoing'
      },
      {
        projectCode: 'AW112',
        projectName: 'Airwater GasWiki Website',
        estimateDate: 'Aug 12, 2022 - Feb 15, 2023',
        technology: 'Ruby/React Native',
        leadTeam: 'React Department',
        status: 'Delivered'
      }
    ]
  }
}
