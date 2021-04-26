<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title> Pa Database </title>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">
    <#list movieList as movie>
        </br>
        </br>

        <div class="container">
            <div class="list-group-item justify-content-between">
                MOVIE: <b>${movie.title}' | ID: ${movie.id} </b>
            </div>
            <div class="list-group-item justify-content-between">
                Genres:
                <div class="col-md-1">
                    <#list movie.genreList as genre>
                        <span class="badge badge-default badge-pill">${genre.name}</span>
                    </#list >
                </div>
            </div>

            <div class="list-group-item justify-content-between">
                Directors
                <span class="badge badge-default badge-pill">${movie.directorList?size}</span>
            </div>
            <#list movie.directorList as director>
                <div class="accordion" id="accordionExample-dir-${director.id}">
                    <div class="card">
                        <div class="card-header" id="headingOne-dir-${director.id}">
                            <h5 class="mb-0">
                                <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseOne-dir-${director.id}" aria-expanded="false" aria-controls="collapseOne-dir-${director.id}">
                                    ${director.name} | ${director.id}
                                </button>
                            </h5>
                        </div>

                        <div id="collapseOne-dir-${director.id}" class="collapse" aria-labelledby="headingOne-dir-${director.id}" data-parent="#accordionExample-dir-${director.id}">
                            <div class="card-body">
                                <p> Birth: ${director.birthDetails} </p>
                                <p> Details: ${director.deathDetails} </p>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>

            <div class="list-group-item justify-content-between">
                Actors
                <span class="badge badge-default badge-pill">${movie.actorList?size}</span>
            </div>
            <#list movie.actorList as director>
                <div class="accordion" id="accordionExample-${director.id}">
                    <div class="card">
                        <div class="card-header" id="headingOne-${director.id}">
                            <h5 class="mb-0">
                                <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseOne-${director.id}" aria-expanded="false" aria-controls="collapseOne-${director.id}">
                                    ${director.name} | ${director.id}
                                </button>
                            </h5>
                        </div>

                        <div id="collapseOne-${director.id}" class="collapse" aria-labelledby="headingOne-${director.id}" data-parent="#accordionExample-${director.id}">
                            <div class="card-body">
                                <p> ${director.birthDetails} </p>
                                <p> Date of birth: ${director.dateOfBirth} </p>
                                <p> Details: ${director.deathDetails} </p>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

    </#list>
</div>
</body>
</html>