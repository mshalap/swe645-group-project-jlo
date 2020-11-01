import { Component, OnInit } from '@angular/core';
import { Survey } from '../survey'
import { ApiService } from '../api.service';

@Component({
  selector: 'app-display-results',
  templateUrl: './display-results.component.html',
  styleUrls: ['./display-results.component.css']
})
export class DisplayResultsComponent implements OnInit {

  title = 'View Surveys';
  surveys:Survey[];

  constructor(private apiService:ApiService) {}

  ngOnInit() { 
    this.refreshSurveys()
  }

  refreshSurveys() {
    this.apiService.getSurveys()
      .subscribe(data => {
        console.log(data)
        this.surveys=data;
      })      
 
  }

}
