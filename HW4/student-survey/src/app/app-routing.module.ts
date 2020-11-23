import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DisplayResultsComponent } from './display-results/display-results.component';
import { TakeSurveyComponent } from './take-survey/take-survey.component';

const routes: Routes = [
  { path: 'take-survey', component: TakeSurveyComponent },
  { path: 'display-results', component: DisplayResultsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
