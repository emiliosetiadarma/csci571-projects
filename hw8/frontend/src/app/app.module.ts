import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HighchartsChartModule } from 'highcharts-angular';

import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SearchComponent } from './pages/search/search.component';
import { WatchlistComponent } from './pages/watchlist/watchlist.component';
import { PortfolioComponent } from './pages/portfolio/portfolio.component';
import { DetailsComponent } from './pages/details/details.component';
import { InvalidComponent } from './components/invalid/invalid.component';
import { PriceTableComponent } from './components/price-table/price-table.component';
import { ChangeArrowComponent } from './components/change-arrow/change-arrow.component';
import { DailyChartComponent } from './components/daily-chart/daily-chart.component';
import { NewsItemComponent } from './components/news-item/news-item.component';
import { NewsGroupComponent } from './components/news-group/news-group.component';
import { HistoricalChartComponent } from './components/historical-chart/historical-chart.component';
import { WatchlistStarComponent } from './components/watchlist-star/watchlist-star.component';
import { BuyModalComponent } from './components/buy-modal/buy-modal.component';
import { NewsModalComponent } from './components/news-modal/news-modal.component';
import { SellModalComponent } from './components/sell-modal/sell-modal.component';
import { BuyButtonComponent } from './components/buy-button/buy-button.component';
import { SellButtonComponent } from './components/sell-button/sell-button.component';
import { WatchlistItemComponent } from './components/watchlist-item/watchlist-item.component';
import { PortfolioItemComponent } from './components/portfolio-item/portfolio-item.component';
import { EmptyWatchlistAlertComponent } from './components/empty-watchlist-alert/empty-watchlist-alert.component';
import { EmptyPortfolioAlertComponent } from './components/empty-portfolio-alert/empty-portfolio-alert.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SearchComponent,
    WatchlistComponent,
    PortfolioComponent,
    DetailsComponent,
    InvalidComponent,
    PriceTableComponent,
    ChangeArrowComponent,
    DailyChartComponent,
    NewsItemComponent,
    NewsGroupComponent,
    HistoricalChartComponent,
    WatchlistStarComponent,
    BuyModalComponent,
    NewsModalComponent,
    SellModalComponent,
    BuyButtonComponent,
    SellButtonComponent,
    WatchlistItemComponent,
    PortfolioItemComponent,
    EmptyWatchlistAlertComponent,
    EmptyPortfolioAlertComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    HighchartsChartModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
