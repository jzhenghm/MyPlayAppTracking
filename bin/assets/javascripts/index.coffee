$ ->
  $.get "/journals", (journals) ->
    $.each journals, (index, journal) ->
      app = $("<div>").addClass("app").text journal.app
      version = $("<div>").addClass("version").text journal.version
      $("#journals").append $("<li>").append(app).append(version)