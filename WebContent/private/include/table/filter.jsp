<div class="fiter-container">
    <div class="row">    
        <div class="col-xs-6 col-xs-offset-2 col-lg-6 col-lg-offset-2">
            <div class="input-group">
                <div class="input-group-btn search-panel">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        <span id="search_concept">Filter by</span> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#username">User Name</a></li>
                        <li><a href="#name">Name</a></li>
                        <li><a href="#role">role</a></li>
                        <li><a href="#registerdOn">Registration Date</a></li>
                        <li class="divider"></li>
                        <li><a href="#all">Anything</a></li>
                    </ul>
                </div>
                <div class="clearfix"></div>
                <div class="input-group-btn search-panel">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        <span id="search_concept">Check</span> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#contains">Contains</a></li>
                        <li><a href="#its_equal">It's equal</a></li>
                        <li><a href="#greather_than">Greater than ></a></li>
                        <li><a href="#less_than">Less than < </a></li>
                        <li class="divider"></li>
                        <li><a href="#all">Anything</a></li>
                    </ul>
                </div>
                <input type="hidden" name="search_param" value="all" id="search_param">         
                <input type="text" class="form-control" name="x" placeholder="Search term...">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
                </span>
            </div>
        </div>
    </div>
</div>