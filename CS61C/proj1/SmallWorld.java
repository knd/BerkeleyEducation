  


<!DOCTYPE html>
<html>
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# githubog: http://ogp.me/ns/fb/githubog#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>cs61c-ef/proj1/SmallWorld.java at master · ucberkeley-cs61c/cs61c-ef</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub" />
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub" />
    <link rel="apple-touch-icon-precomposed" sizes="57x57" href="apple-touch-icon-114.png" />
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="apple-touch-icon-114.png" />
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="apple-touch-icon-144.png" />
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="apple-touch-icon-144.png" />
    <meta name="msapplication-TileImage" content="/windows-tile.png">
    <meta name="msapplication-TileColor" content="#ffffff">

    
    
    <link rel="icon" type="image/x-icon" href="/favicon.ico" />

    <meta content="authenticity_token" name="csrf-param" />
<meta content="v/hEuKrGBaw2jbLCKVZivegL1lD5oKEUU/SBxo+0Q1E=" name="csrf-token" />

    <link href="https://a248.e.akamai.net/assets.github.com/assets/github-ca4b92fb3c82a44dad191edddb200e77ea292bba.css" media="screen" rel="stylesheet" type="text/css" />
    <link href="https://a248.e.akamai.net/assets.github.com/assets/github2-e32cf731b09f22ce8beee0954b580b5d8084ff25.css" media="screen" rel="stylesheet" type="text/css" />
    


    <script src="https://a248.e.akamai.net/assets.github.com/assets/frameworks-57542e0cba19d068168099f287c117efa142863c.js" type="text/javascript"></script>
    <script src="https://a248.e.akamai.net/assets.github.com/assets/github-65c8131db26cee4318983041a0339ebe444a0fd4.js" type="text/javascript"></script>
    

      <link rel='permalink' href='/ucberkeley-cs61c/cs61c-ef/blob/58606b259370b3f3fdd31dd44e553f8af7de6288/proj1/SmallWorld.java'>
    <meta property="og:title" content="cs61c-ef"/>
    <meta property="og:type" content="githubog:gitrepository"/>
    <meta property="og:url" content="https://github.com/ucberkeley-cs61c/cs61c-ef"/>
    <meta property="og:image" content="https://a248.e.akamai.net/assets.github.com/images/gravatars/gravatar-user-420.png?1345673561"/>
    <meta property="og:site_name" content="GitHub"/>
    <meta property="og:description" content="Contribute to cs61c-ef development by creating an account on GitHub."/>

    <meta name="description" content="Contribute to cs61c-ef development by creating an account on GitHub." />

  <link href="https://github.com/ucberkeley-cs61c/cs61c-ef/commits/master.atom?login=knd&token=f5d31ff2c5c191081566ede01c062c0c" rel="alternate" title="Recent Commits to cs61c-ef:master" type="application/atom+xml" />

  </head>


  <body class="logged_in page-blob macintosh vis-private env-production ">
    <div id="wrapper">

      

      

      


        <div class="header header-logged-in true">
          <div class="container clearfix">

            <a class="header-logo-blacktocat" href="https://github.com/">
  <span class="mega-icon mega-icon-blacktocat"></span>
</a>

            <div class="divider-vertical"></div>

            
  <a href="/notifications" class="notification-indicator tooltipped downwards" title="You have unread notifications">
    <span class="mail-status unread"></span>
  </a>
  <div class="divider-vertical"></div>


              
  <div class="topsearch command-bar-activated">
    <form accept-charset="UTF-8" action="/search" class="command_bar_form" id="top_search_form" method="get">
  <a href="/search/advanced" class="advanced-search tooltipped downwards command-bar-search" id="advanced_search" title="Advanced search"><span class="mini-icon mini-icon-advanced-search "></span></a>

  <input type="text" name="q" id="command-bar" placeholder="Search or type a command" tabindex="1" data-username="knd" autocapitalize="off">

  <span class="mini-icon help tooltipped downwards" title="Show command bar help">
    <span class="mini-icon mini-icon-help"></span>
  </span>

  <input type="hidden" name="ref" value="commandbar">

  <div class="divider-vertical"></div>
</form>



    <ul class="top-nav">
        <li class="explore"><a href="https://github.com/explore">Explore</a></li>
        <li><a href="https://gist.github.com">Gist</a></li>
        <li><a href="/blog">Blog</a></li>
      <li><a href="http://help.github.com">Help</a></li>
    </ul>
  </div>


            

  
    <ul id="user-links">
      <li>
        <a href="https://github.com/knd" class="name">
          <img height="20" src="https://secure.gravatar.com/avatar/c15192632bea19ddbcdd0a83d745b51d?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="20" /> knd
        </a>
      </li>
      <li>
        <a href="/new" id="new_repo" class="tooltipped downwards" title="Create a new repo">
          <span class="mini-icon mini-icon-create"></span>
        </a>
      </li>
      <li>
        <a href="/settings/profile" id="account_settings"
          class="tooltipped downwards"
          title="Account settings ">
          <span class="mini-icon mini-icon-account-settings"></span>
        </a>
      </li>
      <li>
          <a href="/logout" data-method="post" id="logout" class="tooltipped downwards" title="Sign out">
            <span class="mini-icon mini-icon-logout"></span>
          </a>
      </li>
    </ul>



            
          </div>
        </div>


      

      


            <div class="site hfeed" itemscope itemtype="http://schema.org/WebPage">
      <div class="hentry">
        
        <div class="pagehead repohead instapaper_ignore readability-menu">
          <div class="container">
            <div class="title-actions-bar">
              


                  <ul class="pagehead-actions">
          <li class="nspr">
            <a href="/ucberkeley-cs61c/cs61c-ef/pull/new/master" class="minibutton btn-pull-request" icon_class="mini-icon-pull-request"><span class="mini-icon mini-icon-pull-request"></span>Pull Request</a>
          </li>

          <li class="subscription">
              <form accept-charset="UTF-8" action="/notifications/subscribe" data-autosubmit="true" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="authenticity_token" type="hidden" value="v/hEuKrGBaw2jbLCKVZivegL1lD5oKEUU/SBxo+0Q1E=" /></div>  <input id="repository_id" name="repository_id" type="hidden" value="3223398" />
  <div class="context-menu-container js-menu-container js-context-menu">
    <span class="minibutton switcher bigger js-menu-target">
      <span class="js-context-button">
          <span class="mini-icon mini-icon-unwatch"></span>Unwatch
      </span>
    </span>

    <div class="context-pane js-menu-content">
      <a href="javascript:;" class="close js-menu-close"><span class="mini-icon mini-icon-remove-close"></span></a>
      <div class="context-title">Notification status</div>

      <div class="context-body pane-selector">
        <ul class="js-navigation-container">
          <li class="selector-item js-navigation-item js-navigation-target ">
            <span class="mini-icon mini-icon-confirm"></span>
            <label>
              <input id="do_included" name="do" type="radio" value="included" />
              <h4>Not watching</h4>
              <p>You will only receive notifications when you participate or are mentioned.</p>
            </label>
            <span class="context-button-text js-context-button-text">
              <span class="mini-icon mini-icon-watching"></span>
              Watch
            </span>
          </li>
          <li class="selector-item js-navigation-item js-navigation-target selected">
            <span class="mini-icon mini-icon-confirm"></span>
            <label>
              <input checked="checked" id="do_subscribed" name="do" type="radio" value="subscribed" />
              <h4>Watching</h4>
              <p>You will receive all notifications for this repository.</p>
            </label>
            <span class="context-button-text js-context-button-text">
              <span class="mini-icon mini-icon-unwatch"></span>
              Unwatch
            </span>
          </li>
          <li class="selector-item js-navigation-item js-navigation-target ">
            <span class="mini-icon mini-icon-confirm"></span>
            <label>
              <input id="do_ignore" name="do" type="radio" value="ignore" />
              <h4>Ignored</h4>
              <p>You will not receive notifications for this repository.</p>
            </label>
            <span class="context-button-text js-context-button-text">
              <span class="mini-icon mini-icon-mute"></span>
              Stop ignoring
            </span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</form>
          </li>

          <li class="js-toggler-container js-social-container starring-container ">
            <a href="/ucberkeley-cs61c/cs61c-ef/unstar" class="minibutton js-toggler-target starred" data-remote="true" data-method="post" rel="nofollow">
              <span class="mini-icon mini-icon-star"></span>Unstar
            </a><a href="/ucberkeley-cs61c/cs61c-ef/star" class="minibutton js-toggler-target unstarred" data-remote="true" data-method="post" rel="nofollow">
              <span class="mini-icon mini-icon-star"></span>Star
            </a><a class="social-count js-social-count" href="/ucberkeley-cs61c/cs61c-ef/stargazers">1</a>
          </li>

              <li>
                <a href="/ucberkeley-cs61c/cs61c-ef/fork_select" class="minibutton js-toggler-target lighter" rel="facebox nofollow"><span class="mini-icon mini-icon-fork"></span>Fork</a><a href="/ucberkeley-cs61c/cs61c-ef/network" class="social-count">0</a>
              </li>


    </ul>

              <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title private">
                <span class="repo-label"><span>private</span></span>
                <span class="mega-icon mega-icon-private-repo"></span>
                <span class="author vcard">
                  <a href="/ucberkeley-cs61c" class="url fn" itemprop="url" rel="author">
                  <span itemprop="title">ucberkeley-cs61c</span>
                  </a></span> /
                <strong><a href="/ucberkeley-cs61c/cs61c-ef" class="js-current-repository">cs61c-ef</a></strong>
              </h1>
            </div>

            

  <ul class="tabs">
    <li><a href="/ucberkeley-cs61c/cs61c-ef" class="selected" highlight="repo_sourcerepo_downloadsrepo_commitsrepo_tagsrepo_branches">Code</a></li>
    <li><a href="/ucberkeley-cs61c/cs61c-ef/network" highlight="repo_network">Network</a></li>
    <li><a href="/ucberkeley-cs61c/cs61c-ef/pulls" highlight="repo_pulls">Pull Requests <span class='counter'>0</span></a></li>

      <li><a href="/ucberkeley-cs61c/cs61c-ef/issues" highlight="repo_issues">Issues <span class='counter'>0</span></a></li>

      <li><a href="/ucberkeley-cs61c/cs61c-ef/wiki" highlight="repo_wiki">Wiki</a></li>


    <li><a href="/ucberkeley-cs61c/cs61c-ef/graphs" highlight="repo_graphsrepo_contributors">Graphs</a></li>


  </ul>
  
<div class="tabnav">

  <span class="tabnav-right">
    <ul class="tabnav-tabs">
      <li><a href="/ucberkeley-cs61c/cs61c-ef/tags" class="tabnav-tab" highlight="repo_tags">Tags <span class="counter ">6</span></a></li>
      <li><a href="/ucberkeley-cs61c/cs61c-ef/downloads" class="tabnav-tab" highlight="repo_downloads">Downloads <span class="counter blank">0</span></a></li>
    </ul>
    
  <div class="tabnav-widget search repo-search ">
    <form accept-charset="UTF-8" action="/ucberkeley-cs61c/cs61c-ef/search" method="get">
      <span class="fieldwrap">
        <input type="text" name="q" value=""
          placeholder="Search source code&hellip;" tabindex="2" autocapitalize="off" /><button type="submit" class="minibutton empty-icon"><span class="mini-icon mini-icon-search-input"></span></button>
      </span>
      <input type="hidden" id="lang-value" name="l" value="" />
      <input type="hidden" id="start-value" name="start" value="" />
</form>  </div>

  </span>

  <div class="tabnav-widget scope">


    <div class="context-menu-container js-menu-container js-context-menu">
      <a href="#"
         class="minibutton bigger switcher js-menu-target js-commitish-button btn-branch repo-tree"
         data-hotkey="w"
         data-ref="master">
         <span><em class="mini-icon mini-icon-branch"></em><i>branch:</i> master</span>
      </a>

      <div class="context-pane commitish-context js-menu-content">
        <a href="javascript:;" class="close js-menu-close"><span class="mini-icon mini-icon-remove-close"></span></a>
        <div class="context-title">Switch branches/tags</div>
        <div class="context-body pane-selector commitish-selector js-navigation-container">
          <div class="filterbar">
            <input type="text" id="context-commitish-filter-field" class="js-navigation-enable js-filterable-field" placeholder="Filter branches/tags">
            <ul class="tabs">
              <li><a href="#" data-filter="branches" class="selected">Branches</a></li>
                <li><a href="#" data-filter="tags">Tags</a></li>
            </ul>
          </div>

          <div class="js-filter-tab js-filter-branches">
            <div data-filterable-for="context-commitish-filter-field" data-filterable-type=substring>
                <div class="commitish-item branch-commitish selector-item js-navigation-item js-navigation-target selected">
                  <span class="mini-icon mini-icon-confirm"></span>
                  <h4>
                      <a href="/ucberkeley-cs61c/cs61c-ef/blob/master/proj1/SmallWorld.java" class="js-navigation-open" data-name="master" rel="nofollow">master</a>
                  </h4>
                </div>
            </div>
            <div class="no-results">Nothing to show</div>
          </div>

            <div class="js-filter-tab js-filter-tags " style="display:none">
              <div data-filterable-for="context-commitish-filter-field" data-filterable-type=substring>
                  <div class="commitish-item tag-commitish selector-item js-navigation-item js-navigation-target ">
                    <span class="mini-icon mini-icon-confirm"></span>
                    <h4>
                        <a href="/ucberkeley-cs61c/cs61c-ef/blob/proj3-2/proj1/SmallWorld.java" class="js-navigation-open" data-name="proj3-2" rel="nofollow">proj3-2</a>
                    </h4>
                  </div>
                  <div class="commitish-item tag-commitish selector-item js-navigation-item js-navigation-target ">
                    <span class="mini-icon mini-icon-confirm"></span>
                    <h4>
                        <a href="/ucberkeley-cs61c/cs61c-ef/blob/proj3-1/proj1/SmallWorld.java" class="js-navigation-open" data-name="proj3-1" rel="nofollow">proj3-1</a>
                    </h4>
                  </div>
                  <div class="commitish-item tag-commitish selector-item js-navigation-item js-navigation-target ">
                    <span class="mini-icon mini-icon-confirm"></span>
                    <h4>
                        <a href="/ucberkeley-cs61c/cs61c-ef/blob/proj2-2/proj1/SmallWorld.java" class="js-navigation-open" data-name="proj2-2" rel="nofollow">proj2-2</a>
                    </h4>
                  </div>
                  <div class="commitish-item tag-commitish selector-item js-navigation-item js-navigation-target ">
                    <span class="mini-icon mini-icon-confirm"></span>
                    <h4>
                        <a href="/ucberkeley-cs61c/cs61c-ef/blob/proj2-1/proj1/SmallWorld.java" class="js-navigation-open" data-name="proj2-1" rel="nofollow">proj2-1</a>
                    </h4>
                  </div>
                  <div class="commitish-item tag-commitish selector-item js-navigation-item js-navigation-target ">
                    <span class="mini-icon mini-icon-confirm"></span>
                    <h4>
                        <a href="/ucberkeley-cs61c/cs61c-ef/blob/proj1-2/proj1/SmallWorld.java" class="js-navigation-open" data-name="proj1-2" rel="nofollow">proj1-2</a>
                    </h4>
                  </div>
                  <div class="commitish-item tag-commitish selector-item js-navigation-item js-navigation-target ">
                    <span class="mini-icon mini-icon-confirm"></span>
                    <h4>
                        <a href="/ucberkeley-cs61c/cs61c-ef/blob/proj1-1/proj1/SmallWorld.java" class="js-navigation-open" data-name="proj1-1" rel="nofollow">proj1-1</a>
                    </h4>
                  </div>
              </div>
              <div class="no-results">Nothing to show</div>
            </div>
        </div>
      </div><!-- /.commitish-context-context -->
    </div>
  </div> <!-- /.scope -->

  <ul class="tabnav-tabs">
    <li><a href="/ucberkeley-cs61c/cs61c-ef" class="selected tabnav-tab" highlight="repo_source">Files</a></li>
    <li><a href="/ucberkeley-cs61c/cs61c-ef/commits/master" class="tabnav-tab" highlight="repo_commits">Commits</a></li>
    <li><a href="/ucberkeley-cs61c/cs61c-ef/branches" class="tabnav-tab" highlight="repo_branches" rel="nofollow">Branches <span class="counter ">1</span></a></li>
  </ul>

</div>

  
  
  


            
          </div>
        </div><!-- /.repohead -->

        <div id="js-repo-pjax-container" class="container context-loader-container" data-pjax-container>
          


<!-- blob contrib key: blob_contributors:v21:e13c7f40abd5faef642e5135a9237079 -->
<!-- blob contrib frag key: views10/v8/blob_contributors:v21:e13c7f40abd5faef642e5135a9237079 -->

<div id="slider">


    <p title="This is a placeholder element" class="js-history-link-replace hidden"></p>
    <div class="breadcrumb" data-path="proj1/SmallWorld.java/">
      <span class='bold'><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/ucberkeley-cs61c/cs61c-ef" class="js-slider-breadcrumb" itemscope="url"><span itemprop="title">cs61c-ef</span></a></span></span> / <span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/ucberkeley-cs61c/cs61c-ef/tree/master/proj1" class="js-slider-breadcrumb" itemscope="url"><span itemprop="title">proj1</span></a></span> / <strong class="final-path">SmallWorld.java</strong> <span class="js-clippy mini-icon mini-icon-clippy " data-clipboard-text="proj1/SmallWorld.java" data-copied-hint="copied!" data-copy-hint="copy to clipboard"></span>
    </div>

    <a href="/ucberkeley-cs61c/cs61c-ef/find/master" class="js-slide-to" data-hotkey="t" style="display:none">Show File Finder</a>

      
  <div class="commit file-history-tease" data-path="proj1/SmallWorld.java/">
    <img class="main-avatar" height="24" src="https://secure.gravatar.com/avatar/c15192632bea19ddbcdd0a83d745b51d?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="24" />
    <span class="author"><a href="/knd">knd</a></span>
    <time class="js-relative-date" datetime="2012-02-07T23:31:41-08:00" title="2012-02-07 23:31:41">February 07, 2012</time>
    <div class="commit-title">
        <a href="/ucberkeley-cs61c/cs61c-ef/commit/fe42bb070435541acc47fc9907ee7529a8ebbd76" class="message">submit</a>
    </div>

    <div class="participation">
      <p class="quickstat"><a href="#blob_contributors_box" rel="facebox"><strong>1</strong> contributor</a></p>
      
    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2>Users on GitHub who have contributed to this file</h2>
      <ul class="facebox-user-list">
        <li>
          <img height="24" src="https://secure.gravatar.com/avatar/c15192632bea19ddbcdd0a83d745b51d?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png" width="24" />
          <a href="/knd">knd</a>
        </li>
      </ul>
    </div>
  </div>


    <div class="frames">
      <div class="frame frame-center" data-path="proj1/SmallWorld.java/" data-permalink-url="/ucberkeley-cs61c/cs61c-ef/blob/58606b259370b3f3fdd31dd44e553f8af7de6288/proj1/SmallWorld.java" data-title="cs61c-ef/proj1/SmallWorld.java at master · ucberkeley-cs61c/cs61c-ef · GitHub" data-type="blob">

        <div id="files" class="bubble">
          <div class="file">
            <div class="meta">
              <div class="info">
                <span class="icon"><b class="mini-icon mini-icon-text-file"></b></span>
                <span class="mode" title="File Mode">file</span>
                  <span>419 lines (345 sloc)</span>
                <span>14.746 kb</span>
              </div>
              <ul class="button-group actions">
                  <li>
                        <a class="grouped-button minibutton bigger lighter"
                           href="/ucberkeley-cs61c/cs61c-ef/edit/master/proj1/SmallWorld.java"
                           data-method="post" rel="nofollow" data-hotkey="e">Edit</a>
                  </li>
                <li><a href="/ucberkeley-cs61c/cs61c-ef/raw/master/proj1/SmallWorld.java" class="minibutton grouped-button bigger lighter" id="raw-url">Raw</a></li>
                  <li><a href="/ucberkeley-cs61c/cs61c-ef/blame/master/proj1/SmallWorld.java" class="minibutton grouped-button bigger lighter">Blame</a></li>
                <li><a href="/ucberkeley-cs61c/cs61c-ef/commits/master/proj1/SmallWorld.java" class="minibutton grouped-button bigger lighter" rel="nofollow">History</a></li>
              </ul>
            </div>
                <div class="data type-java">
      <table cellpadding="0" cellspacing="0" class="lines">
        <tr>
          <td>
            <pre class="line_numbers"><span id="L1" rel="#L1">1</span>
<span id="L2" rel="#L2">2</span>
<span id="L3" rel="#L3">3</span>
<span id="L4" rel="#L4">4</span>
<span id="L5" rel="#L5">5</span>
<span id="L6" rel="#L6">6</span>
<span id="L7" rel="#L7">7</span>
<span id="L8" rel="#L8">8</span>
<span id="L9" rel="#L9">9</span>
<span id="L10" rel="#L10">10</span>
<span id="L11" rel="#L11">11</span>
<span id="L12" rel="#L12">12</span>
<span id="L13" rel="#L13">13</span>
<span id="L14" rel="#L14">14</span>
<span id="L15" rel="#L15">15</span>
<span id="L16" rel="#L16">16</span>
<span id="L17" rel="#L17">17</span>
<span id="L18" rel="#L18">18</span>
<span id="L19" rel="#L19">19</span>
<span id="L20" rel="#L20">20</span>
<span id="L21" rel="#L21">21</span>
<span id="L22" rel="#L22">22</span>
<span id="L23" rel="#L23">23</span>
<span id="L24" rel="#L24">24</span>
<span id="L25" rel="#L25">25</span>
<span id="L26" rel="#L26">26</span>
<span id="L27" rel="#L27">27</span>
<span id="L28" rel="#L28">28</span>
<span id="L29" rel="#L29">29</span>
<span id="L30" rel="#L30">30</span>
<span id="L31" rel="#L31">31</span>
<span id="L32" rel="#L32">32</span>
<span id="L33" rel="#L33">33</span>
<span id="L34" rel="#L34">34</span>
<span id="L35" rel="#L35">35</span>
<span id="L36" rel="#L36">36</span>
<span id="L37" rel="#L37">37</span>
<span id="L38" rel="#L38">38</span>
<span id="L39" rel="#L39">39</span>
<span id="L40" rel="#L40">40</span>
<span id="L41" rel="#L41">41</span>
<span id="L42" rel="#L42">42</span>
<span id="L43" rel="#L43">43</span>
<span id="L44" rel="#L44">44</span>
<span id="L45" rel="#L45">45</span>
<span id="L46" rel="#L46">46</span>
<span id="L47" rel="#L47">47</span>
<span id="L48" rel="#L48">48</span>
<span id="L49" rel="#L49">49</span>
<span id="L50" rel="#L50">50</span>
<span id="L51" rel="#L51">51</span>
<span id="L52" rel="#L52">52</span>
<span id="L53" rel="#L53">53</span>
<span id="L54" rel="#L54">54</span>
<span id="L55" rel="#L55">55</span>
<span id="L56" rel="#L56">56</span>
<span id="L57" rel="#L57">57</span>
<span id="L58" rel="#L58">58</span>
<span id="L59" rel="#L59">59</span>
<span id="L60" rel="#L60">60</span>
<span id="L61" rel="#L61">61</span>
<span id="L62" rel="#L62">62</span>
<span id="L63" rel="#L63">63</span>
<span id="L64" rel="#L64">64</span>
<span id="L65" rel="#L65">65</span>
<span id="L66" rel="#L66">66</span>
<span id="L67" rel="#L67">67</span>
<span id="L68" rel="#L68">68</span>
<span id="L69" rel="#L69">69</span>
<span id="L70" rel="#L70">70</span>
<span id="L71" rel="#L71">71</span>
<span id="L72" rel="#L72">72</span>
<span id="L73" rel="#L73">73</span>
<span id="L74" rel="#L74">74</span>
<span id="L75" rel="#L75">75</span>
<span id="L76" rel="#L76">76</span>
<span id="L77" rel="#L77">77</span>
<span id="L78" rel="#L78">78</span>
<span id="L79" rel="#L79">79</span>
<span id="L80" rel="#L80">80</span>
<span id="L81" rel="#L81">81</span>
<span id="L82" rel="#L82">82</span>
<span id="L83" rel="#L83">83</span>
<span id="L84" rel="#L84">84</span>
<span id="L85" rel="#L85">85</span>
<span id="L86" rel="#L86">86</span>
<span id="L87" rel="#L87">87</span>
<span id="L88" rel="#L88">88</span>
<span id="L89" rel="#L89">89</span>
<span id="L90" rel="#L90">90</span>
<span id="L91" rel="#L91">91</span>
<span id="L92" rel="#L92">92</span>
<span id="L93" rel="#L93">93</span>
<span id="L94" rel="#L94">94</span>
<span id="L95" rel="#L95">95</span>
<span id="L96" rel="#L96">96</span>
<span id="L97" rel="#L97">97</span>
<span id="L98" rel="#L98">98</span>
<span id="L99" rel="#L99">99</span>
<span id="L100" rel="#L100">100</span>
<span id="L101" rel="#L101">101</span>
<span id="L102" rel="#L102">102</span>
<span id="L103" rel="#L103">103</span>
<span id="L104" rel="#L104">104</span>
<span id="L105" rel="#L105">105</span>
<span id="L106" rel="#L106">106</span>
<span id="L107" rel="#L107">107</span>
<span id="L108" rel="#L108">108</span>
<span id="L109" rel="#L109">109</span>
<span id="L110" rel="#L110">110</span>
<span id="L111" rel="#L111">111</span>
<span id="L112" rel="#L112">112</span>
<span id="L113" rel="#L113">113</span>
<span id="L114" rel="#L114">114</span>
<span id="L115" rel="#L115">115</span>
<span id="L116" rel="#L116">116</span>
<span id="L117" rel="#L117">117</span>
<span id="L118" rel="#L118">118</span>
<span id="L119" rel="#L119">119</span>
<span id="L120" rel="#L120">120</span>
<span id="L121" rel="#L121">121</span>
<span id="L122" rel="#L122">122</span>
<span id="L123" rel="#L123">123</span>
<span id="L124" rel="#L124">124</span>
<span id="L125" rel="#L125">125</span>
<span id="L126" rel="#L126">126</span>
<span id="L127" rel="#L127">127</span>
<span id="L128" rel="#L128">128</span>
<span id="L129" rel="#L129">129</span>
<span id="L130" rel="#L130">130</span>
<span id="L131" rel="#L131">131</span>
<span id="L132" rel="#L132">132</span>
<span id="L133" rel="#L133">133</span>
<span id="L134" rel="#L134">134</span>
<span id="L135" rel="#L135">135</span>
<span id="L136" rel="#L136">136</span>
<span id="L137" rel="#L137">137</span>
<span id="L138" rel="#L138">138</span>
<span id="L139" rel="#L139">139</span>
<span id="L140" rel="#L140">140</span>
<span id="L141" rel="#L141">141</span>
<span id="L142" rel="#L142">142</span>
<span id="L143" rel="#L143">143</span>
<span id="L144" rel="#L144">144</span>
<span id="L145" rel="#L145">145</span>
<span id="L146" rel="#L146">146</span>
<span id="L147" rel="#L147">147</span>
<span id="L148" rel="#L148">148</span>
<span id="L149" rel="#L149">149</span>
<span id="L150" rel="#L150">150</span>
<span id="L151" rel="#L151">151</span>
<span id="L152" rel="#L152">152</span>
<span id="L153" rel="#L153">153</span>
<span id="L154" rel="#L154">154</span>
<span id="L155" rel="#L155">155</span>
<span id="L156" rel="#L156">156</span>
<span id="L157" rel="#L157">157</span>
<span id="L158" rel="#L158">158</span>
<span id="L159" rel="#L159">159</span>
<span id="L160" rel="#L160">160</span>
<span id="L161" rel="#L161">161</span>
<span id="L162" rel="#L162">162</span>
<span id="L163" rel="#L163">163</span>
<span id="L164" rel="#L164">164</span>
<span id="L165" rel="#L165">165</span>
<span id="L166" rel="#L166">166</span>
<span id="L167" rel="#L167">167</span>
<span id="L168" rel="#L168">168</span>
<span id="L169" rel="#L169">169</span>
<span id="L170" rel="#L170">170</span>
<span id="L171" rel="#L171">171</span>
<span id="L172" rel="#L172">172</span>
<span id="L173" rel="#L173">173</span>
<span id="L174" rel="#L174">174</span>
<span id="L175" rel="#L175">175</span>
<span id="L176" rel="#L176">176</span>
<span id="L177" rel="#L177">177</span>
<span id="L178" rel="#L178">178</span>
<span id="L179" rel="#L179">179</span>
<span id="L180" rel="#L180">180</span>
<span id="L181" rel="#L181">181</span>
<span id="L182" rel="#L182">182</span>
<span id="L183" rel="#L183">183</span>
<span id="L184" rel="#L184">184</span>
<span id="L185" rel="#L185">185</span>
<span id="L186" rel="#L186">186</span>
<span id="L187" rel="#L187">187</span>
<span id="L188" rel="#L188">188</span>
<span id="L189" rel="#L189">189</span>
<span id="L190" rel="#L190">190</span>
<span id="L191" rel="#L191">191</span>
<span id="L192" rel="#L192">192</span>
<span id="L193" rel="#L193">193</span>
<span id="L194" rel="#L194">194</span>
<span id="L195" rel="#L195">195</span>
<span id="L196" rel="#L196">196</span>
<span id="L197" rel="#L197">197</span>
<span id="L198" rel="#L198">198</span>
<span id="L199" rel="#L199">199</span>
<span id="L200" rel="#L200">200</span>
<span id="L201" rel="#L201">201</span>
<span id="L202" rel="#L202">202</span>
<span id="L203" rel="#L203">203</span>
<span id="L204" rel="#L204">204</span>
<span id="L205" rel="#L205">205</span>
<span id="L206" rel="#L206">206</span>
<span id="L207" rel="#L207">207</span>
<span id="L208" rel="#L208">208</span>
<span id="L209" rel="#L209">209</span>
<span id="L210" rel="#L210">210</span>
<span id="L211" rel="#L211">211</span>
<span id="L212" rel="#L212">212</span>
<span id="L213" rel="#L213">213</span>
<span id="L214" rel="#L214">214</span>
<span id="L215" rel="#L215">215</span>
<span id="L216" rel="#L216">216</span>
<span id="L217" rel="#L217">217</span>
<span id="L218" rel="#L218">218</span>
<span id="L219" rel="#L219">219</span>
<span id="L220" rel="#L220">220</span>
<span id="L221" rel="#L221">221</span>
<span id="L222" rel="#L222">222</span>
<span id="L223" rel="#L223">223</span>
<span id="L224" rel="#L224">224</span>
<span id="L225" rel="#L225">225</span>
<span id="L226" rel="#L226">226</span>
<span id="L227" rel="#L227">227</span>
<span id="L228" rel="#L228">228</span>
<span id="L229" rel="#L229">229</span>
<span id="L230" rel="#L230">230</span>
<span id="L231" rel="#L231">231</span>
<span id="L232" rel="#L232">232</span>
<span id="L233" rel="#L233">233</span>
<span id="L234" rel="#L234">234</span>
<span id="L235" rel="#L235">235</span>
<span id="L236" rel="#L236">236</span>
<span id="L237" rel="#L237">237</span>
<span id="L238" rel="#L238">238</span>
<span id="L239" rel="#L239">239</span>
<span id="L240" rel="#L240">240</span>
<span id="L241" rel="#L241">241</span>
<span id="L242" rel="#L242">242</span>
<span id="L243" rel="#L243">243</span>
<span id="L244" rel="#L244">244</span>
<span id="L245" rel="#L245">245</span>
<span id="L246" rel="#L246">246</span>
<span id="L247" rel="#L247">247</span>
<span id="L248" rel="#L248">248</span>
<span id="L249" rel="#L249">249</span>
<span id="L250" rel="#L250">250</span>
<span id="L251" rel="#L251">251</span>
<span id="L252" rel="#L252">252</span>
<span id="L253" rel="#L253">253</span>
<span id="L254" rel="#L254">254</span>
<span id="L255" rel="#L255">255</span>
<span id="L256" rel="#L256">256</span>
<span id="L257" rel="#L257">257</span>
<span id="L258" rel="#L258">258</span>
<span id="L259" rel="#L259">259</span>
<span id="L260" rel="#L260">260</span>
<span id="L261" rel="#L261">261</span>
<span id="L262" rel="#L262">262</span>
<span id="L263" rel="#L263">263</span>
<span id="L264" rel="#L264">264</span>
<span id="L265" rel="#L265">265</span>
<span id="L266" rel="#L266">266</span>
<span id="L267" rel="#L267">267</span>
<span id="L268" rel="#L268">268</span>
<span id="L269" rel="#L269">269</span>
<span id="L270" rel="#L270">270</span>
<span id="L271" rel="#L271">271</span>
<span id="L272" rel="#L272">272</span>
<span id="L273" rel="#L273">273</span>
<span id="L274" rel="#L274">274</span>
<span id="L275" rel="#L275">275</span>
<span id="L276" rel="#L276">276</span>
<span id="L277" rel="#L277">277</span>
<span id="L278" rel="#L278">278</span>
<span id="L279" rel="#L279">279</span>
<span id="L280" rel="#L280">280</span>
<span id="L281" rel="#L281">281</span>
<span id="L282" rel="#L282">282</span>
<span id="L283" rel="#L283">283</span>
<span id="L284" rel="#L284">284</span>
<span id="L285" rel="#L285">285</span>
<span id="L286" rel="#L286">286</span>
<span id="L287" rel="#L287">287</span>
<span id="L288" rel="#L288">288</span>
<span id="L289" rel="#L289">289</span>
<span id="L290" rel="#L290">290</span>
<span id="L291" rel="#L291">291</span>
<span id="L292" rel="#L292">292</span>
<span id="L293" rel="#L293">293</span>
<span id="L294" rel="#L294">294</span>
<span id="L295" rel="#L295">295</span>
<span id="L296" rel="#L296">296</span>
<span id="L297" rel="#L297">297</span>
<span id="L298" rel="#L298">298</span>
<span id="L299" rel="#L299">299</span>
<span id="L300" rel="#L300">300</span>
<span id="L301" rel="#L301">301</span>
<span id="L302" rel="#L302">302</span>
<span id="L303" rel="#L303">303</span>
<span id="L304" rel="#L304">304</span>
<span id="L305" rel="#L305">305</span>
<span id="L306" rel="#L306">306</span>
<span id="L307" rel="#L307">307</span>
<span id="L308" rel="#L308">308</span>
<span id="L309" rel="#L309">309</span>
<span id="L310" rel="#L310">310</span>
<span id="L311" rel="#L311">311</span>
<span id="L312" rel="#L312">312</span>
<span id="L313" rel="#L313">313</span>
<span id="L314" rel="#L314">314</span>
<span id="L315" rel="#L315">315</span>
<span id="L316" rel="#L316">316</span>
<span id="L317" rel="#L317">317</span>
<span id="L318" rel="#L318">318</span>
<span id="L319" rel="#L319">319</span>
<span id="L320" rel="#L320">320</span>
<span id="L321" rel="#L321">321</span>
<span id="L322" rel="#L322">322</span>
<span id="L323" rel="#L323">323</span>
<span id="L324" rel="#L324">324</span>
<span id="L325" rel="#L325">325</span>
<span id="L326" rel="#L326">326</span>
<span id="L327" rel="#L327">327</span>
<span id="L328" rel="#L328">328</span>
<span id="L329" rel="#L329">329</span>
<span id="L330" rel="#L330">330</span>
<span id="L331" rel="#L331">331</span>
<span id="L332" rel="#L332">332</span>
<span id="L333" rel="#L333">333</span>
<span id="L334" rel="#L334">334</span>
<span id="L335" rel="#L335">335</span>
<span id="L336" rel="#L336">336</span>
<span id="L337" rel="#L337">337</span>
<span id="L338" rel="#L338">338</span>
<span id="L339" rel="#L339">339</span>
<span id="L340" rel="#L340">340</span>
<span id="L341" rel="#L341">341</span>
<span id="L342" rel="#L342">342</span>
<span id="L343" rel="#L343">343</span>
<span id="L344" rel="#L344">344</span>
<span id="L345" rel="#L345">345</span>
<span id="L346" rel="#L346">346</span>
<span id="L347" rel="#L347">347</span>
<span id="L348" rel="#L348">348</span>
<span id="L349" rel="#L349">349</span>
<span id="L350" rel="#L350">350</span>
<span id="L351" rel="#L351">351</span>
<span id="L352" rel="#L352">352</span>
<span id="L353" rel="#L353">353</span>
<span id="L354" rel="#L354">354</span>
<span id="L355" rel="#L355">355</span>
<span id="L356" rel="#L356">356</span>
<span id="L357" rel="#L357">357</span>
<span id="L358" rel="#L358">358</span>
<span id="L359" rel="#L359">359</span>
<span id="L360" rel="#L360">360</span>
<span id="L361" rel="#L361">361</span>
<span id="L362" rel="#L362">362</span>
<span id="L363" rel="#L363">363</span>
<span id="L364" rel="#L364">364</span>
<span id="L365" rel="#L365">365</span>
<span id="L366" rel="#L366">366</span>
<span id="L367" rel="#L367">367</span>
<span id="L368" rel="#L368">368</span>
<span id="L369" rel="#L369">369</span>
<span id="L370" rel="#L370">370</span>
<span id="L371" rel="#L371">371</span>
<span id="L372" rel="#L372">372</span>
<span id="L373" rel="#L373">373</span>
<span id="L374" rel="#L374">374</span>
<span id="L375" rel="#L375">375</span>
<span id="L376" rel="#L376">376</span>
<span id="L377" rel="#L377">377</span>
<span id="L378" rel="#L378">378</span>
<span id="L379" rel="#L379">379</span>
<span id="L380" rel="#L380">380</span>
<span id="L381" rel="#L381">381</span>
<span id="L382" rel="#L382">382</span>
<span id="L383" rel="#L383">383</span>
<span id="L384" rel="#L384">384</span>
<span id="L385" rel="#L385">385</span>
<span id="L386" rel="#L386">386</span>
<span id="L387" rel="#L387">387</span>
<span id="L388" rel="#L388">388</span>
<span id="L389" rel="#L389">389</span>
<span id="L390" rel="#L390">390</span>
<span id="L391" rel="#L391">391</span>
<span id="L392" rel="#L392">392</span>
<span id="L393" rel="#L393">393</span>
<span id="L394" rel="#L394">394</span>
<span id="L395" rel="#L395">395</span>
<span id="L396" rel="#L396">396</span>
<span id="L397" rel="#L397">397</span>
<span id="L398" rel="#L398">398</span>
<span id="L399" rel="#L399">399</span>
<span id="L400" rel="#L400">400</span>
<span id="L401" rel="#L401">401</span>
<span id="L402" rel="#L402">402</span>
<span id="L403" rel="#L403">403</span>
<span id="L404" rel="#L404">404</span>
<span id="L405" rel="#L405">405</span>
<span id="L406" rel="#L406">406</span>
<span id="L407" rel="#L407">407</span>
<span id="L408" rel="#L408">408</span>
<span id="L409" rel="#L409">409</span>
<span id="L410" rel="#L410">410</span>
<span id="L411" rel="#L411">411</span>
<span id="L412" rel="#L412">412</span>
<span id="L413" rel="#L413">413</span>
<span id="L414" rel="#L414">414</span>
<span id="L415" rel="#L415">415</span>
<span id="L416" rel="#L416">416</span>
<span id="L417" rel="#L417">417</span>
<span id="L418" rel="#L418">418</span>
</pre>
          </td>
          <td width="100%">
                <div class="highlight"><pre><div class='line' id='LC1'><span class="cm">/*</span></div><div class='line' id='LC2'><span class="cm">  CS 61C Project1: Small World</span></div><div class='line' id='LC3'><br/></div><div class='line' id='LC4'><span class="cm">  Name: Khanh Dao</span></div><div class='line' id='LC5'><span class="cm">  Login: cs61c-ef</span></div><div class='line' id='LC6'><br/></div><div class='line' id='LC7'><span class="cm">  Name: Hai-Phuc Huynh</span></div><div class='line' id='LC8'><span class="cm">  Login: cs61c-cd</span></div><div class='line' id='LC9'><span class="cm"> */</span></div><div class='line' id='LC10'><br/></div><div class='line' id='LC11'><br/></div><div class='line' id='LC12'><span class="kn">import</span> <span class="nn">java.io.BufferedReader</span><span class="o">;</span></div><div class='line' id='LC13'><span class="kn">import</span> <span class="nn">java.io.BufferedWriter</span><span class="o">;</span></div><div class='line' id='LC14'><span class="kn">import</span> <span class="nn">java.io.DataInput</span><span class="o">;</span></div><div class='line' id='LC15'><span class="kn">import</span> <span class="nn">java.io.DataOutput</span><span class="o">;</span></div><div class='line' id='LC16'><span class="kn">import</span> <span class="nn">java.io.FileReader</span><span class="o">;</span></div><div class='line' id='LC17'><span class="kn">import</span> <span class="nn">java.io.FileWriter</span><span class="o">;</span></div><div class='line' id='LC18'><span class="kn">import</span> <span class="nn">java.io.IOException</span><span class="o">;</span></div><div class='line' id='LC19'><span class="kn">import</span> <span class="nn">java.lang.Math</span><span class="o">;</span></div><div class='line' id='LC20'><span class="kn">import</span> <span class="nn">java.util.*</span><span class="o">;</span></div><div class='line' id='LC21'><br/></div><div class='line' id='LC22'><span class="kn">import</span> <span class="nn">org.apache.hadoop.conf.Configuration</span><span class="o">;</span></div><div class='line' id='LC23'><span class="kn">import</span> <span class="nn">org.apache.hadoop.filecache.DistributedCache</span><span class="o">;</span></div><div class='line' id='LC24'><span class="kn">import</span> <span class="nn">org.apache.hadoop.fs.FileSystem</span><span class="o">;</span></div><div class='line' id='LC25'><span class="kn">import</span> <span class="nn">org.apache.hadoop.fs.Path</span><span class="o">;</span></div><div class='line' id='LC26'><span class="kn">import</span> <span class="nn">org.apache.hadoop.io.Text</span><span class="o">;</span></div><div class='line' id='LC27'><span class="kn">import</span> <span class="nn">org.apache.hadoop.io.LongWritable</span><span class="o">;</span></div><div class='line' id='LC28'><span class="kn">import</span> <span class="nn">org.apache.hadoop.io.Writable</span><span class="o">;</span></div><div class='line' id='LC29'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.Job</span><span class="o">;</span></div><div class='line' id='LC30'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.Mapper</span><span class="o">;</span></div><div class='line' id='LC31'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.Reducer</span><span class="o">;</span></div><div class='line' id='LC32'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.lib.input.FileInputFormat</span><span class="o">;</span></div><div class='line' id='LC33'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.lib.input.TextInputFormat</span><span class="o">;</span></div><div class='line' id='LC34'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat</span><span class="o">;</span></div><div class='line' id='LC35'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.lib.output.FileOutputFormat</span><span class="o">;</span></div><div class='line' id='LC36'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.lib.output.TextOutputFormat</span><span class="o">;</span></div><div class='line' id='LC37'><span class="kn">import</span> <span class="nn">org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat</span><span class="o">;</span></div><div class='line' id='LC38'><span class="kn">import</span> <span class="nn">org.apache.hadoop.util.GenericOptionsParser</span><span class="o">;</span></div><div class='line' id='LC39'><br/></div><div class='line' id='LC40'><br/></div><div class='line' id='LC41'><span class="kd">public</span> <span class="kd">class</span> <span class="nc">SmallWorld</span> <span class="o">{</span></div><div class='line' id='LC42'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Maximum dept for any breadth-first search</span></div><div class='line' id='LC43'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">final</span> <span class="kt">int</span> <span class="n">MAX_ITERATIONS</span> <span class="o">=</span> <span class="mi">20</span><span class="o">;</span></div><div class='line' id='LC44'><br/></div><div class='line' id='LC45'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Skeleton code uses this to share denom cmd-line arg across cluster</span></div><div class='line' id='LC46'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">final</span> <span class="n">String</span> <span class="n">DENOM_PATH</span> <span class="o">=</span> <span class="s">&quot;denom.txt&quot;</span><span class="o">;</span></div><div class='line' id='LC47'><br/></div><div class='line' id='LC48'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Example enumerated type, used by EValue and Counter example</span></div><div class='line' id='LC49'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">enum</span> <span class="n">ValueUse</span> <span class="o">{</span><span class="n">CURR_NODE</span><span class="o">};</span></div><div class='line' id='LC50'><br/></div><div class='line' id='LC51'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Example writable type</span></div><div class='line' id='LC52'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">EValue</span> <span class="kd">implements</span> <span class="n">Writable</span> <span class="o">{</span></div><div class='line' id='LC53'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="n">ValueUse</span> <span class="n">use</span><span class="o">;</span></div><div class='line' id='LC54'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">long</span> <span class="n">value</span><span class="o">;</span></div><div class='line' id='LC55'><br/></div><div class='line' id='LC56'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="nf">EValue</span><span class="o">(</span><span class="n">ValueUse</span> <span class="n">use</span><span class="o">,</span> <span class="kt">long</span> <span class="n">value</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC57'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">this</span><span class="o">.</span><span class="na">use</span> <span class="o">=</span> <span class="n">use</span><span class="o">;</span></div><div class='line' id='LC58'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">this</span><span class="o">.</span><span class="na">value</span> <span class="o">=</span> <span class="n">value</span><span class="o">;</span></div><div class='line' id='LC59'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC60'><br/></div><div class='line' id='LC61'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="nf">EValue</span><span class="o">()</span> <span class="o">{</span></div><div class='line' id='LC62'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">this</span><span class="o">(</span><span class="n">ValueUse</span><span class="o">.</span><span class="na">CURR_NODE</span><span class="o">,</span> <span class="mi">0</span><span class="o">);</span></div><div class='line' id='LC63'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC64'><br/></div><div class='line' id='LC65'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Serializes object - needed for Writable</span></div><div class='line' id='LC66'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">void</span> <span class="nf">write</span><span class="o">(</span><span class="n">DataOutput</span> <span class="n">out</span><span class="o">)</span> <span class="kd">throws</span> <span class="n">IOException</span> <span class="o">{</span></div><div class='line' id='LC67'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">out</span><span class="o">.</span><span class="na">writeUTF</span><span class="o">(</span><span class="n">use</span><span class="o">.</span><span class="na">name</span><span class="o">());</span></div><div class='line' id='LC68'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">out</span><span class="o">.</span><span class="na">writeLong</span><span class="o">(</span><span class="n">value</span><span class="o">);</span></div><div class='line' id='LC69'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC70'><br/></div><div class='line' id='LC71'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Deserializes object - needed for Writable</span></div><div class='line' id='LC72'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">void</span> <span class="nf">readFields</span><span class="o">(</span><span class="n">DataInput</span> <span class="n">in</span><span class="o">)</span> <span class="kd">throws</span> <span class="n">IOException</span> <span class="o">{</span></div><div class='line' id='LC73'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">use</span> <span class="o">=</span> <span class="n">ValueUse</span><span class="o">.</span><span class="na">valueOf</span><span class="o">(</span><span class="n">in</span><span class="o">.</span><span class="na">readUTF</span><span class="o">());</span></div><div class='line' id='LC74'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">value</span> <span class="o">=</span> <span class="n">in</span><span class="o">.</span><span class="na">readLong</span><span class="o">();</span></div><div class='line' id='LC75'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC76'><br/></div><div class='line' id='LC77'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">void</span> <span class="nf">set</span><span class="o">(</span><span class="n">ValueUse</span> <span class="n">use</span><span class="o">,</span> <span class="kt">long</span> <span class="n">value</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC78'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">this</span><span class="o">.</span><span class="na">use</span> <span class="o">=</span> <span class="n">use</span><span class="o">;</span></div><div class='line' id='LC79'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">this</span><span class="o">.</span><span class="na">value</span> <span class="o">=</span> <span class="n">value</span><span class="o">;</span></div><div class='line' id='LC80'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC81'><br/></div><div class='line' id='LC82'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="n">String</span> <span class="nf">toString</span><span class="o">()</span> <span class="o">{</span></div><div class='line' id='LC83'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">return</span> <span class="n">use</span><span class="o">.</span><span class="na">name</span><span class="o">()</span> <span class="o">+</span> <span class="s">&quot;: &quot;</span> <span class="o">+</span> <span class="n">value</span><span class="o">;</span></div><div class='line' id='LC84'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC85'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC86'><br/></div><div class='line' id='LC87'><br/></div><div class='line' id='LC88'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="cm">/* This example mapper loads in all edges but only propagates a subset.</span></div><div class='line' id='LC89'><span class="cm">       You will need to modify this to propagate all edges, but it is </span></div><div class='line' id='LC90'><span class="cm">       included to demonstate how to read &amp; use the denom argument.         */</span></div><div class='line' id='LC91'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">LoaderMap</span> <span class="kd">extends</span> <span class="n">Mapper</span><span class="o">&lt;</span><span class="n">LongWritable</span><span class="o">,</span> <span class="n">LongWritable</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">&gt;</span> <span class="o">{</span></div><div class='line' id='LC92'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">long</span> <span class="n">denom</span><span class="o">;</span></div><div class='line' id='LC93'><br/></div><div class='line' id='LC94'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="cm">/* Setup is called automatically once per map task. This will</span></div><div class='line' id='LC95'><span class="cm">           read denom in from the DistributedCache, and it will be</span></div><div class='line' id='LC96'><span class="cm">           available to each call of map later on via the instance</span></div><div class='line' id='LC97'><span class="cm">           variable.                                                  */</span></div><div class='line' id='LC98'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nd">@Override</span></div><div class='line' id='LC99'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">void</span> <span class="nf">setup</span><span class="o">(</span><span class="n">Context</span> <span class="n">context</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC100'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">try</span> <span class="o">{</span></div><div class='line' id='LC101'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">Configuration</span> <span class="n">conf</span> <span class="o">=</span> <span class="n">context</span><span class="o">.</span><span class="na">getConfiguration</span><span class="o">();</span></div><div class='line' id='LC102'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">Path</span> <span class="n">cachedDenomPath</span> <span class="o">=</span> <span class="n">DistributedCache</span><span class="o">.</span><span class="na">getLocalCacheFiles</span><span class="o">(</span><span class="n">conf</span><span class="o">)[</span><span class="mi">0</span><span class="o">];</span></div><div class='line' id='LC103'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">BufferedReader</span> <span class="n">reader</span> <span class="o">=</span> <span class="k">new</span> <span class="n">BufferedReader</span><span class="o">(</span></div><div class='line' id='LC104'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">new</span> <span class="nf">FileReader</span><span class="o">(</span><span class="n">cachedDenomPath</span><span class="o">.</span><span class="na">toString</span><span class="o">()));</span></div><div class='line' id='LC105'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">String</span> <span class="n">denomStr</span> <span class="o">=</span> <span class="n">reader</span><span class="o">.</span><span class="na">readLine</span><span class="o">();</span></div><div class='line' id='LC106'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">reader</span><span class="o">.</span><span class="na">close</span><span class="o">();</span></div><div class='line' id='LC107'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">denom</span> <span class="o">=</span> <span class="n">Long</span><span class="o">.</span><span class="na">decode</span><span class="o">(</span><span class="n">denomStr</span><span class="o">);</span></div><div class='line' id='LC108'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span> <span class="k">catch</span> <span class="o">(</span><span class="n">IOException</span> <span class="n">ioe</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC109'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">System</span><span class="o">.</span><span class="na">err</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="s">&quot;IOException reading denom from distributed cache&quot;</span><span class="o">);</span></div><div class='line' id='LC110'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">System</span><span class="o">.</span><span class="na">err</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="n">ioe</span><span class="o">.</span><span class="na">toString</span><span class="o">());</span></div><div class='line' id='LC111'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC112'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC113'><br/></div><div class='line' id='LC114'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="cm">/* Will need to modify to not loose any edges. */</span></div><div class='line' id='LC115'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nd">@Override</span></div><div class='line' id='LC116'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">void</span> <span class="nf">map</span><span class="o">(</span><span class="n">LongWritable</span> <span class="n">key</span><span class="o">,</span> <span class="n">LongWritable</span> <span class="n">value</span><span class="o">,</span> <span class="n">Context</span> <span class="n">context</span><span class="o">)</span></div><div class='line' id='LC117'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">throws</span> <span class="n">IOException</span><span class="o">,</span> <span class="n">InterruptedException</span> <span class="o">{</span></div><div class='line' id='LC118'>&nbsp;	    </div><div class='line' id='LC119'>	    <span class="n">String</span> <span class="n">edge</span> <span class="o">=</span> <span class="n">key</span><span class="o">.</span><span class="na">toString</span><span class="o">()</span> <span class="o">+</span> <span class="s">&quot;,&quot;</span> <span class="o">+</span> <span class="n">value</span><span class="o">.</span><span class="na">toString</span><span class="o">();</span></div><div class='line' id='LC120'>	    <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="s">&quot;graph&quot;</span><span class="o">),</span> <span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">edge</span><span class="o">));</span>	</div><div class='line' id='LC121'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Example of using a counter (counter tagged by EDGE)</span></div><div class='line' id='LC122'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">//context.getCounter(ValueUse.EDGE).increment(1);</span></div><div class='line' id='LC123'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC124'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC125'><br/></div><div class='line' id='LC126'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">LoaderReduce</span> <span class="kd">extends</span> <span class="n">Reducer</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span></div><div class='line' id='LC127'>			<span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">&gt;</span> <span class="o">{</span></div><div class='line' id='LC128'>	<span class="kd">public</span> <span class="kt">long</span> <span class="n">denom</span><span class="o">;</span></div><div class='line' id='LC129'><br/></div><div class='line' id='LC130'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="cm">/* Setup is called automatically once per map task. This will</span></div><div class='line' id='LC131'><span class="cm">           read denom in from the DistributedCache, and it will be</span></div><div class='line' id='LC132'><span class="cm">           available to each call of map later on via the instance</span></div><div class='line' id='LC133'><span class="cm">           variable.                                                  */</span></div><div class='line' id='LC134'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="nd">@Override</span></div><div class='line' id='LC135'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kt">void</span> <span class="nf">setup</span><span class="o">(</span><span class="n">Context</span> <span class="n">context</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC136'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">try</span> <span class="o">{</span></div><div class='line' id='LC137'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">Configuration</span> <span class="n">conf</span> <span class="o">=</span> <span class="n">context</span><span class="o">.</span><span class="na">getConfiguration</span><span class="o">();</span></div><div class='line' id='LC138'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">Path</span> <span class="n">cachedDenomPath</span> <span class="o">=</span> <span class="n">DistributedCache</span><span class="o">.</span><span class="na">getLocalCacheFiles</span><span class="o">(</span><span class="n">conf</span><span class="o">)[</span><span class="mi">0</span><span class="o">];</span></div><div class='line' id='LC139'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">BufferedReader</span> <span class="n">reader</span> <span class="o">=</span> <span class="k">new</span> <span class="n">BufferedReader</span><span class="o">(</span></div><div class='line' id='LC140'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">new</span> <span class="nf">FileReader</span><span class="o">(</span><span class="n">cachedDenomPath</span><span class="o">.</span><span class="na">toString</span><span class="o">()));</span></div><div class='line' id='LC141'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">String</span> <span class="n">denomStr</span> <span class="o">=</span> <span class="n">reader</span><span class="o">.</span><span class="na">readLine</span><span class="o">();</span></div><div class='line' id='LC142'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">reader</span><span class="o">.</span><span class="na">close</span><span class="o">();</span></div><div class='line' id='LC143'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">denom</span> <span class="o">=</span> <span class="n">Long</span><span class="o">.</span><span class="na">decode</span><span class="o">(</span><span class="n">denomStr</span><span class="o">);</span></div><div class='line' id='LC144'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span> <span class="k">catch</span> <span class="o">(</span><span class="n">IOException</span> <span class="n">ioe</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC145'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">System</span><span class="o">.</span><span class="na">err</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="s">&quot;IOException reading denom from distributed cache&quot;</span><span class="o">);</span></div><div class='line' id='LC146'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">System</span><span class="o">.</span><span class="na">err</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="n">ioe</span><span class="o">.</span><span class="na">toString</span><span class="o">());</span></div><div class='line' id='LC147'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC148'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC149'><br/></div><div class='line' id='LC150'>	<span class="nd">@Override</span></div><div class='line' id='LC151'>	<span class="kd">public</span> <span class="kt">void</span> <span class="nf">reduce</span><span class="o">(</span><span class="n">Text</span> <span class="n">key</span><span class="o">,</span> <span class="n">Iterable</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">&gt;</span> <span class="n">values</span><span class="o">,</span></div><div class='line' id='LC152'>		<span class="n">Context</span> <span class="n">context</span><span class="o">)</span> <span class="kd">throws</span> <span class="n">IOException</span><span class="o">,</span> <span class="n">InterruptedException</span> <span class="o">{</span></div><div class='line' id='LC153'>	    <span class="n">Set</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;</span> <span class="n">setStarts</span> <span class="o">=</span> <span class="k">new</span> <span class="n">HashSet</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;();</span></div><div class='line' id='LC154'>	    <span class="n">List</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">&gt;</span> <span class="n">array</span> <span class="o">=</span> <span class="k">new</span> <span class="n">LinkedList</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">&gt;();</span></div><div class='line' id='LC155'>	    <span class="k">for</span> <span class="o">(</span><span class="n">Text</span> <span class="n">value</span> <span class="o">:</span> <span class="n">values</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC156'>		<span class="n">array</span><span class="o">.</span><span class="na">add</span><span class="o">(</span><span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">value</span><span class="o">));</span></div><div class='line' id='LC157'>	    <span class="o">}</span></div><div class='line' id='LC158'><br/></div><div class='line' id='LC159'>	    <span class="k">for</span> <span class="o">(</span><span class="n">Text</span> <span class="n">value</span> <span class="o">:</span> <span class="n">array</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC160'>		<span class="n">String</span> <span class="n">edge</span> <span class="o">=</span> <span class="n">value</span><span class="o">.</span><span class="na">toString</span><span class="o">();</span></div><div class='line' id='LC161'>		<span class="n">String</span> <span class="o">[]</span> <span class="n">tokens</span> <span class="o">=</span> <span class="n">edge</span><span class="o">.</span><span class="na">split</span><span class="o">(</span><span class="s">&quot;,&quot;</span><span class="o">);</span></div><div class='line' id='LC162'>		<span class="k">if</span> <span class="o">(!</span><span class="n">setStarts</span><span class="o">.</span><span class="na">contains</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]))</span> <span class="o">{</span></div><div class='line' id='LC163'>			<span class="k">if</span> <span class="o">(</span><span class="n">Math</span><span class="o">.</span><span class="na">random</span><span class="o">()</span> <span class="o">&lt;</span> <span class="mf">1.0</span><span class="o">/</span><span class="n">denom</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC164'>			    <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]),</span> <span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]</span> <span class="o">+</span> <span class="s">&quot;,dist0,curr&quot;</span><span class="o">));</span></div><div class='line' id='LC165'>			    <span class="k">for</span> <span class="o">(</span><span class="n">Text</span> <span class="n">v</span> <span class="o">:</span> <span class="n">array</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC166'>				<span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]),</span> <span class="n">v</span><span class="o">);</span></div><div class='line' id='LC167'>			    <span class="o">}</span></div><div class='line' id='LC168'>			<span class="o">}</span></div><div class='line' id='LC169'>		<span class="o">}</span></div><div class='line' id='LC170'>	  	<span class="n">setStarts</span><span class="o">.</span><span class="na">add</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]);</span></div><div class='line' id='LC171'>	    <span class="o">}</span></div><div class='line' id='LC172'>	<span class="o">}</span></div><div class='line' id='LC173'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC174'><br/></div><div class='line' id='LC175'><br/></div><div class='line' id='LC176'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="cm">/* Insert your mapreduces here</span></div><div class='line' id='LC177'><span class="cm">       (still feel free to edit elsewhere) */</span></div><div class='line' id='LC178'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">CalcDistMap</span> <span class="kd">extends</span> <span class="n">Mapper</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">&gt;</span> <span class="o">{</span></div><div class='line' id='LC179'><br/></div><div class='line' id='LC180'>	<span class="nd">@Override</span></div><div class='line' id='LC181'>	<span class="kd">public</span> <span class="kt">void</span> <span class="nf">map</span><span class="o">(</span><span class="n">Text</span> <span class="n">key</span><span class="o">,</span> <span class="n">Text</span> <span class="n">value</span><span class="o">,</span> <span class="n">Context</span> <span class="n">context</span><span class="o">)</span></div><div class='line' id='LC182'>		<span class="kd">throws</span> <span class="n">IOException</span><span class="o">,</span> <span class="n">InterruptedException</span> <span class="o">{</span></div><div class='line' id='LC183'>	    <span class="c1">// PHASE II CODE:</span></div><div class='line' id='LC184'>	    <span class="n">String</span> <span class="n">temp</span> <span class="o">=</span> <span class="n">value</span><span class="o">.</span><span class="na">toString</span><span class="o">();</span></div><div class='line' id='LC185'>	    <span class="k">if</span> <span class="o">(</span><span class="n">temp</span><span class="o">.</span><span class="na">contains</span><span class="o">(</span><span class="s">&quot;curr&quot;</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC186'>		<span class="n">context</span><span class="o">.</span><span class="na">getCounter</span><span class="o">(</span><span class="n">ValueUse</span><span class="o">.</span><span class="na">CURR_NODE</span><span class="o">).</span><span class="na">increment</span><span class="o">(</span><span class="mi">1</span><span class="o">);</span></div><div class='line' id='LC187'>	    <span class="o">}</span></div><div class='line' id='LC188'>	    <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">key</span><span class="o">,</span> <span class="n">value</span><span class="o">);</span></div><div class='line' id='LC189'>	<span class="o">}</span></div><div class='line' id='LC190'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC191'><br/></div><div class='line' id='LC192'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">CalcDistReduce</span> <span class="kd">extends</span> <span class="n">Reducer</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">&gt;</span> <span class="o">{</span></div><div class='line' id='LC193'><br/></div><div class='line' id='LC194'>	<span class="nd">@Override</span></div><div class='line' id='LC195'>	<span class="kd">public</span> <span class="kt">void</span> <span class="nf">reduce</span><span class="o">(</span><span class="n">Text</span> <span class="n">key</span><span class="o">,</span> <span class="n">Iterable</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">&gt;</span> <span class="n">values</span><span class="o">,</span> </div><div class='line' id='LC196'>		<span class="n">Context</span> <span class="n">context</span><span class="o">)</span> <span class="kd">throws</span> <span class="n">IOException</span><span class="o">,</span> <span class="n">InterruptedException</span> <span class="o">{</span></div><div class='line' id='LC197'>	    <span class="c1">// PHASE II CODE:</span></div><div class='line' id='LC198'>	    <span class="n">Map</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">String</span><span class="o">&gt;</span> <span class="n">visitedNodes</span> <span class="o">=</span> <span class="k">new</span> <span class="n">HashMap</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">String</span><span class="o">&gt;();</span></div><div class='line' id='LC199'>	    <span class="n">Map</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;&gt;</span> <span class="n">edges</span> <span class="o">=</span> <span class="k">new</span> <span class="n">HashMap</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;&gt;();</span></div><div class='line' id='LC200'>	    <span class="n">Map</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">String</span><span class="o">&gt;</span> <span class="n">currNodes</span> <span class="o">=</span> <span class="k">new</span> <span class="n">HashMap</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">String</span><span class="o">&gt;();</span></div><div class='line' id='LC201'>	    <span class="kt">int</span> <span class="n">currNum</span> <span class="o">=</span> <span class="mi">0</span><span class="o">;</span></div><div class='line' id='LC202'>	    <span class="kt">int</span> <span class="n">newCurrNum</span> <span class="o">=</span> <span class="mi">0</span><span class="o">;</span></div><div class='line' id='LC203'><br/></div><div class='line' id='LC204'>	    <span class="k">for</span> <span class="o">(</span><span class="n">Text</span> <span class="n">value</span> <span class="o">:</span> <span class="n">values</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC205'>		<span class="n">String</span> <span class="n">temp</span> <span class="o">=</span> <span class="n">value</span><span class="o">.</span><span class="na">toString</span><span class="o">();</span></div><div class='line' id='LC206'>	    	<span class="n">String</span> <span class="o">[]</span> <span class="n">tokens</span> <span class="o">=</span> <span class="n">temp</span><span class="o">.</span><span class="na">split</span><span class="o">(</span><span class="s">&quot;,&quot;</span><span class="o">);</span></div><div class='line' id='LC207'>		<span class="k">if</span> <span class="o">(</span><span class="n">temp</span><span class="o">.</span><span class="na">contains</span><span class="o">(</span><span class="s">&quot;dist&quot;</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC208'>		    <span class="k">if</span> <span class="o">(</span><span class="n">temp</span><span class="o">.</span><span class="na">contains</span><span class="o">(</span><span class="s">&quot;curr&quot;</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC209'>			<span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">key</span><span class="o">,</span> <span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]</span> <span class="o">+</span> <span class="s">&quot;,&quot;</span> <span class="o">+</span> <span class="n">tokens</span><span class="o">[</span><span class="mi">1</span><span class="o">]));</span> <span class="c1">// emit here</span></div><div class='line' id='LC210'>			<span class="n">currNodes</span><span class="o">.</span><span class="na">put</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">],</span> <span class="n">tokens</span><span class="o">[</span><span class="mi">1</span><span class="o">]);</span></div><div class='line' id='LC211'>			<span class="n">currNum</span> <span class="o">+=</span> <span class="mi">1</span><span class="o">;</span> <span class="c1">// decrement here</span></div><div class='line' id='LC212'>	  	    <span class="o">}</span> <span class="k">else</span> <span class="o">{</span></div><div class='line' id='LC213'>		        <span class="n">visitedNodes</span><span class="o">.</span><span class="na">put</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">],</span> <span class="n">tokens</span><span class="o">[</span><span class="mi">1</span><span class="o">]);</span></div><div class='line' id='LC214'>		        <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">key</span><span class="o">,</span> <span class="n">value</span><span class="o">);</span> <span class="c1">// emit here </span></div><div class='line' id='LC215'>		    <span class="o">}</span></div><div class='line' id='LC216'>		<span class="o">}</span> <span class="k">else</span> <span class="o">{</span> <span class="c1">// not contains &quot;dist&quot;</span></div><div class='line' id='LC217'>		    <span class="k">if</span> <span class="o">(</span><span class="n">edges</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]))</span> <span class="o">{</span> <span class="c1">// already has linked list</span></div><div class='line' id='LC218'>			<span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;</span> <span class="n">tempNeighbors</span> <span class="o">=</span> <span class="n">edges</span><span class="o">.</span><span class="na">get</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">]);</span></div><div class='line' id='LC219'>			<span class="n">tempNeighbors</span><span class="o">.</span><span class="na">add</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">1</span><span class="o">]);</span></div><div class='line' id='LC220'>		    <span class="o">}</span> <span class="k">else</span> <span class="o">{</span> <span class="c1">// does not have linked list</span></div><div class='line' id='LC221'>			<span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;</span> <span class="n">neighbors</span> <span class="o">=</span> <span class="k">new</span> <span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;();</span></div><div class='line' id='LC222'>			<span class="n">neighbors</span><span class="o">.</span><span class="na">add</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">1</span><span class="o">]);</span></div><div class='line' id='LC223'>			<span class="n">edges</span><span class="o">.</span><span class="na">put</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">0</span><span class="o">],</span> <span class="n">neighbors</span><span class="o">);</span></div><div class='line' id='LC224'>		    <span class="o">}</span></div><div class='line' id='LC225'>		<span class="o">}</span></div><div class='line' id='LC226'>	    <span class="o">}</span> <span class="c1">// finished saving values</span></div><div class='line' id='LC227'><br/></div><div class='line' id='LC228'><br/></div><div class='line' id='LC229'>	    <span class="k">for</span> <span class="o">(</span><span class="n">Map</span><span class="o">.</span><span class="na">Entry</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">String</span><span class="o">&gt;</span> <span class="n">currNodeEntry</span> <span class="o">:</span> <span class="n">currNodes</span><span class="o">.</span><span class="na">entrySet</span><span class="o">())</span> <span class="o">{</span></div><div class='line' id='LC230'>		<span class="n">String</span> <span class="n">node</span> <span class="o">=</span> <span class="n">currNodeEntry</span><span class="o">.</span><span class="na">getKey</span><span class="o">();</span></div><div class='line' id='LC231'>		<span class="n">String</span> <span class="n">distance</span> <span class="o">=</span> <span class="n">currNodeEntry</span><span class="o">.</span><span class="na">getValue</span><span class="o">();</span></div><div class='line' id='LC232'><br/></div><div class='line' id='LC233'>		    <span class="k">if</span> <span class="o">(</span><span class="n">edges</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">node</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC234'>		        <span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;</span> <span class="n">tempNeighbors</span> <span class="o">=</span> <span class="n">edges</span><span class="o">.</span><span class="na">get</span><span class="o">(</span><span class="n">node</span><span class="o">);</span></div><div class='line' id='LC235'>			<span class="kt">int</span> <span class="n">dist</span> <span class="o">=</span> <span class="n">Integer</span><span class="o">.</span><span class="na">parseInt</span><span class="o">(</span><span class="n">distance</span><span class="o">.</span><span class="na">substring</span><span class="o">(</span><span class="mi">4</span><span class="o">))</span> <span class="o">+</span> <span class="mi">1</span><span class="o">;</span></div><div class='line' id='LC236'>			<span class="k">for</span> <span class="o">(</span><span class="n">String</span> <span class="n">neighbor</span> <span class="o">:</span> <span class="n">tempNeighbors</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC237'>			    <span class="k">if</span> <span class="o">(!</span><span class="n">visitedNodes</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">neighbor</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC238'>				<span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">key</span><span class="o">,</span> <span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">neighbor</span> <span class="o">+</span> <span class="s">&quot;,dist&quot;</span> <span class="o">+</span> <span class="n">Integer</span><span class="o">.</span><span class="na">toString</span><span class="o">(</span><span class="n">dist</span><span class="o">)</span> <span class="o">+</span> <span class="s">&quot;,curr&quot;</span><span class="o">));</span>  <span class="c1">// emit here</span></div><div class='line' id='LC239'>				<span class="n">newCurrNum</span> <span class="o">+=</span> <span class="mi">1</span><span class="o">;</span></div><div class='line' id='LC240'>				<span class="c1">//visitedNodes.put(neighbor, &quot;dist&quot; + Integer.toString(dist));</span></div><div class='line' id='LC241'>			    <span class="o">}</span></div><div class='line' id='LC242'>			<span class="o">}</span></div><div class='line' id='LC243'>		    <span class="o">}</span></div><div class='line' id='LC244'><br/></div><div class='line' id='LC245'>	    <span class="o">}</span> <span class="c1">// finished iterating	</span></div><div class='line' id='LC246'><br/></div><div class='line' id='LC247'>	    <span class="n">context</span><span class="o">.</span><span class="na">getCounter</span><span class="o">(</span><span class="n">ValueUse</span><span class="o">.</span><span class="na">CURR_NODE</span><span class="o">).</span><span class="na">increment</span><span class="o">(</span><span class="n">newCurrNum</span> <span class="o">-</span> <span class="n">currNum</span><span class="o">);</span> </div><div class='line' id='LC248'><br/></div><div class='line' id='LC249'>	    <span class="cm">/*</span></div><div class='line' id='LC250'><span class="cm">   	    for (Map.Entry&lt;String, String&gt; nodeEntry : addingVisitedNodes.entrySet()) {</span></div><div class='line' id='LC251'><span class="cm">		String node = nodeEntry.getKey();</span></div><div class='line' id='LC252'><span class="cm">		String distance = nodeEntry.getValue();</span></div><div class='line' id='LC253'><span class="cm">		visitedNodes.put(node, distance);</span></div><div class='line' id='LC254'><span class="cm">	    } // finished adding</span></div><div class='line' id='LC255'><span class="cm">	    */</span></div><div class='line' id='LC256'><br/></div><div class='line' id='LC257'>	    <span class="k">for</span> <span class="o">(</span><span class="n">Map</span><span class="o">.</span><span class="na">Entry</span><span class="o">&lt;</span><span class="n">String</span><span class="o">,</span> <span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;&gt;</span> <span class="n">edgeEntry</span> <span class="o">:</span> <span class="n">edges</span><span class="o">.</span><span class="na">entrySet</span><span class="o">())</span> <span class="o">{</span></div><div class='line' id='LC258'>		<span class="n">String</span> <span class="n">node</span> <span class="o">=</span> <span class="n">edgeEntry</span><span class="o">.</span><span class="na">getKey</span><span class="o">();</span></div><div class='line' id='LC259'>		<span class="n">ArrayList</span><span class="o">&lt;</span><span class="n">String</span><span class="o">&gt;</span> <span class="n">neighbors</span> <span class="o">=</span> <span class="n">edgeEntry</span><span class="o">.</span><span class="na">getValue</span><span class="o">();</span></div><div class='line' id='LC260'>		<span class="k">if</span> <span class="o">(!</span><span class="n">visitedNodes</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">node</span><span class="o">)</span> <span class="o">&amp;&amp;</span> <span class="o">!</span><span class="n">currNodes</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">node</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC261'>		    <span class="k">for</span> <span class="o">(</span><span class="n">String</span> <span class="n">toNode</span> <span class="o">:</span> <span class="n">neighbors</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC262'>			<span class="k">if</span> <span class="o">(!</span><span class="n">visitedNodes</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">node</span><span class="o">)</span> <span class="o">&amp;&amp;</span> <span class="o">!</span><span class="n">currNodes</span><span class="o">.</span><span class="na">containsKey</span><span class="o">(</span><span class="n">node</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC263'>			    <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">key</span><span class="o">,</span> <span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">node</span> <span class="o">+</span> <span class="s">&quot;,&quot;</span> <span class="o">+</span> <span class="n">toNode</span><span class="o">));</span></div><div class='line' id='LC264'>			<span class="o">}</span></div><div class='line' id='LC265'>		    <span class="o">}</span></div><div class='line' id='LC266'>		<span class="o">}</span></div><div class='line' id='LC267'>	    <span class="o">}</span></div><div class='line' id='LC268'><br/></div><div class='line' id='LC269'><br/></div><div class='line' id='LC270'>	<span class="o">}</span>	    </div><div class='line' id='LC271'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC272'>&nbsp;&nbsp;&nbsp;&nbsp;</div><div class='line' id='LC273'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">//////////// PHASE III:</span></div><div class='line' id='LC274'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">HistMap</span> <span class="kd">extends</span> <span class="n">Mapper</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">LongWritable</span><span class="o">&gt;</span> <span class="o">{</span></div><div class='line' id='LC275'><br/></div><div class='line' id='LC276'>	<span class="nd">@Override</span></div><div class='line' id='LC277'>	<span class="kd">public</span> <span class="kt">void</span> <span class="nf">map</span><span class="o">(</span><span class="n">Text</span> <span class="n">key</span><span class="o">,</span> <span class="n">Text</span> <span class="n">value</span><span class="o">,</span> <span class="n">Context</span> <span class="n">context</span><span class="o">)</span></div><div class='line' id='LC278'>		<span class="kd">throws</span> <span class="n">IOException</span><span class="o">,</span> <span class="n">InterruptedException</span> <span class="o">{</span></div><div class='line' id='LC279'>	    <span class="n">String</span> <span class="n">temp</span> <span class="o">=</span> <span class="n">value</span><span class="o">.</span><span class="na">toString</span><span class="o">();</span></div><div class='line' id='LC280'>	    <span class="k">if</span> <span class="o">(</span><span class="n">temp</span><span class="o">.</span><span class="na">contains</span><span class="o">(</span><span class="s">&quot;dist&quot;</span><span class="o">))</span> <span class="o">{</span></div><div class='line' id='LC281'>	        <span class="n">String</span> <span class="o">[]</span> <span class="n">tokens</span> <span class="o">=</span> <span class="n">temp</span><span class="o">.</span><span class="na">split</span><span class="o">(</span><span class="s">&quot;,&quot;</span><span class="o">);</span></div><div class='line' id='LC282'>	        <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="k">new</span> <span class="n">Text</span><span class="o">(</span><span class="n">tokens</span><span class="o">[</span><span class="mi">1</span><span class="o">].</span><span class="na">substring</span><span class="o">(</span><span class="mi">4</span><span class="o">)),</span> <span class="k">new</span> <span class="n">LongWritable</span><span class="o">(</span><span class="mi">1</span><span class="o">));</span></div><div class='line' id='LC283'>	    <span class="o">}</span></div><div class='line' id='LC284'>	<span class="o">}</span></div><div class='line' id='LC285'><br/></div><div class='line' id='LC286'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC287'><br/></div><div class='line' id='LC288'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kd">class</span> <span class="nc">HistReduce</span> <span class="kd">extends</span> <span class="n">Reducer</span><span class="o">&lt;</span><span class="n">Text</span><span class="o">,</span> <span class="n">LongWritable</span><span class="o">,</span> <span class="n">Text</span><span class="o">,</span> <span class="n">LongWritable</span><span class="o">&gt;</span> <span class="o">{</span></div><div class='line' id='LC289'><br/></div><div class='line' id='LC290'>	<span class="nd">@Override</span></div><div class='line' id='LC291'>	<span class="kd">public</span> <span class="kt">void</span> <span class="nf">reduce</span><span class="o">(</span><span class="n">Text</span> <span class="n">key</span><span class="o">,</span> <span class="n">Iterable</span><span class="o">&lt;</span><span class="n">LongWritable</span><span class="o">&gt;</span> <span class="n">values</span><span class="o">,</span> </div><div class='line' id='LC292'>		<span class="n">Context</span> <span class="n">context</span><span class="o">)</span> <span class="kd">throws</span> <span class="n">IOException</span><span class="o">,</span> <span class="n">InterruptedException</span> <span class="o">{</span></div><div class='line' id='LC293'>	    <span class="kt">long</span> <span class="n">sum</span> <span class="o">=</span> <span class="mi">0</span><span class="o">;</span></div><div class='line' id='LC294'>	    <span class="k">for</span> <span class="o">(</span><span class="n">LongWritable</span> <span class="n">value</span> <span class="o">:</span> <span class="n">values</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC295'>		<span class="n">sum</span> <span class="o">+=</span> <span class="n">value</span><span class="o">.</span><span class="na">get</span><span class="o">();</span></div><div class='line' id='LC296'>	    <span class="o">}</span></div><div class='line' id='LC297'>	    <span class="n">context</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">key</span><span class="o">,</span> <span class="k">new</span> <span class="n">LongWritable</span><span class="o">(</span><span class="n">sum</span><span class="o">));</span></div><div class='line' id='LC298'><br/></div><div class='line' id='LC299'>&nbsp;&nbsp;	<span class="o">}</span></div><div class='line' id='LC300'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC301'><br/></div><div class='line' id='LC302'><br/></div><div class='line' id='LC303'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Shares denom argument across the cluster via DistributedCache</span></div><div class='line' id='LC304'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kt">void</span> <span class="nf">shareDenom</span><span class="o">(</span><span class="n">String</span> <span class="n">denomStr</span><span class="o">,</span> <span class="n">Configuration</span> <span class="n">conf</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC305'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">try</span> <span class="o">{</span></div><div class='line' id='LC306'>	    <span class="n">Path</span> <span class="n">localDenomPath</span> <span class="o">=</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="n">DENOM_PATH</span> <span class="o">+</span> <span class="s">&quot;-source&quot;</span><span class="o">);</span></div><div class='line' id='LC307'>	    <span class="n">Path</span> <span class="n">remoteDenomPath</span> <span class="o">=</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="n">DENOM_PATH</span><span class="o">);</span></div><div class='line' id='LC308'>	    <span class="n">BufferedWriter</span> <span class="n">writer</span> <span class="o">=</span> <span class="k">new</span> <span class="n">BufferedWriter</span><span class="o">(</span></div><div class='line' id='LC309'>				    <span class="k">new</span> <span class="nf">FileWriter</span><span class="o">(</span><span class="n">localDenomPath</span><span class="o">.</span><span class="na">toString</span><span class="o">()));</span></div><div class='line' id='LC310'>	    <span class="n">writer</span><span class="o">.</span><span class="na">write</span><span class="o">(</span><span class="n">denomStr</span><span class="o">);</span></div><div class='line' id='LC311'>	    <span class="n">writer</span><span class="o">.</span><span class="na">newLine</span><span class="o">();</span></div><div class='line' id='LC312'>	    <span class="n">writer</span><span class="o">.</span><span class="na">close</span><span class="o">();</span></div><div class='line' id='LC313'>	    <span class="n">FileSystem</span> <span class="n">fs</span> <span class="o">=</span> <span class="n">FileSystem</span><span class="o">.</span><span class="na">get</span><span class="o">(</span><span class="n">conf</span><span class="o">);</span></div><div class='line' id='LC314'>	    <span class="n">fs</span><span class="o">.</span><span class="na">copyFromLocalFile</span><span class="o">(</span><span class="kc">true</span><span class="o">,</span><span class="kc">true</span><span class="o">,</span><span class="n">localDenomPath</span><span class="o">,</span><span class="n">remoteDenomPath</span><span class="o">);</span></div><div class='line' id='LC315'>	    <span class="n">DistributedCache</span><span class="o">.</span><span class="na">addCacheFile</span><span class="o">(</span><span class="n">remoteDenomPath</span><span class="o">.</span><span class="na">toUri</span><span class="o">(),</span> <span class="n">conf</span><span class="o">);</span></div><div class='line' id='LC316'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span> <span class="k">catch</span> <span class="o">(</span><span class="n">IOException</span> <span class="n">ioe</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC317'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">System</span><span class="o">.</span><span class="na">err</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="s">&quot;IOException writing to distributed cache&quot;</span><span class="o">);</span></div><div class='line' id='LC318'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">System</span><span class="o">.</span><span class="na">err</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="n">ioe</span><span class="o">.</span><span class="na">toString</span><span class="o">());</span></div><div class='line' id='LC319'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC320'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC321'><br/></div><div class='line' id='LC322'><br/></div><div class='line' id='LC323'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">public</span> <span class="kd">static</span> <span class="kt">void</span> <span class="nf">main</span><span class="o">(</span><span class="n">String</span><span class="o">[]</span> <span class="n">rawArgs</span><span class="o">)</span> <span class="kd">throws</span> <span class="n">Exception</span> <span class="o">{</span></div><div class='line' id='LC324'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">GenericOptionsParser</span> <span class="n">parser</span> <span class="o">=</span> <span class="k">new</span> <span class="n">GenericOptionsParser</span><span class="o">(</span><span class="n">rawArgs</span><span class="o">);</span></div><div class='line' id='LC325'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">Configuration</span> <span class="n">conf</span> <span class="o">=</span> <span class="n">parser</span><span class="o">.</span><span class="na">getConfiguration</span><span class="o">();</span></div><div class='line' id='LC326'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">String</span><span class="o">[]</span> <span class="n">args</span> <span class="o">=</span> <span class="n">parser</span><span class="o">.</span><span class="na">getRemainingArgs</span><span class="o">();</span></div><div class='line' id='LC327'><br/></div><div class='line' id='LC328'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Set denom from command line arguments</span></div><div class='line' id='LC329'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">shareDenom</span><span class="o">(</span><span class="n">args</span><span class="o">[</span><span class="mi">2</span><span class="o">],</span> <span class="n">conf</span><span class="o">);</span></div><div class='line' id='LC330'><br/></div><div class='line' id='LC331'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Setting up mapreduce job to load in graph</span></div><div class='line' id='LC332'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">Job</span> <span class="n">job</span> <span class="o">=</span> <span class="k">new</span> <span class="n">Job</span><span class="o">(</span><span class="n">conf</span><span class="o">,</span> <span class="s">&quot;load graph&quot;</span><span class="o">);</span></div><div class='line' id='LC333'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setJarByClass</span><span class="o">(</span><span class="n">SmallWorld</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC334'><br/></div><div class='line' id='LC335'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapOutputKeyClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC336'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapOutputValueClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC337'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputKeyClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC338'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputValueClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC339'><br/></div><div class='line' id='LC340'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapperClass</span><span class="o">(</span><span class="n">LoaderMap</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC341'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setReducerClass</span><span class="o">(</span><span class="n">LoaderReduce</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC342'><br/></div><div class='line' id='LC343'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setInputFormatClass</span><span class="o">(</span><span class="n">SequenceFileInputFormat</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC344'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputFormatClass</span><span class="o">(</span><span class="n">SequenceFileOutputFormat</span><span class="o">.</span><span class="na">class</span><span class="o">);</span>  <span class="c1">// Change for TEST</span></div><div class='line' id='LC345'><br/></div><div class='line' id='LC346'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Input from command-line argument, output to predictable place</span></div><div class='line' id='LC347'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">FileInputFormat</span><span class="o">.</span><span class="na">addInputPath</span><span class="o">(</span><span class="n">job</span><span class="o">,</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="n">args</span><span class="o">[</span><span class="mi">0</span><span class="o">]));</span></div><div class='line' id='LC348'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">FileOutputFormat</span><span class="o">.</span><span class="na">setOutputPath</span><span class="o">(</span><span class="n">job</span><span class="o">,</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="s">&quot;bfs-0-out&quot;</span><span class="o">));</span></div><div class='line' id='LC349'><br/></div><div class='line' id='LC350'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Actually starts job, and waits for it to finish</span></div><div class='line' id='LC351'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">waitForCompletion</span><span class="o">(</span><span class="kc">true</span><span class="o">);</span></div><div class='line' id='LC352'><br/></div><div class='line' id='LC353'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Example of reading a counter</span></div><div class='line' id='LC354'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">//System.out.println(&quot;Read in &quot; + </span></div><div class='line' id='LC355'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">//          job.getCounters().findCounter(ValueUse.EDGE).getValue() + </span></div><div class='line' id='LC356'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">//                 &quot; edges&quot;);</span></div><div class='line' id='LC357'><br/></div><div class='line' id='LC358'><br/></div><div class='line' id='LC359'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Repeats your BFS mapreduce</span></div><div class='line' id='LC360'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kt">int</span> <span class="n">i</span><span class="o">=</span><span class="mi">0</span><span class="o">;</span></div><div class='line' id='LC361'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Will need to change terminating conditions to respond to data</span></div><div class='line' id='LC362'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">while</span> <span class="o">(</span><span class="n">i</span><span class="o">&lt;</span><span class="n">MAX_ITERATIONS</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC363'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span> <span class="o">=</span> <span class="k">new</span> <span class="n">Job</span><span class="o">(</span><span class="n">conf</span><span class="o">,</span> <span class="s">&quot;bfs&quot;</span> <span class="o">+</span> <span class="n">i</span><span class="o">);</span></div><div class='line' id='LC364'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setJarByClass</span><span class="o">(</span><span class="n">SmallWorld</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC365'><br/></div><div class='line' id='LC366'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapOutputKeyClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC367'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapOutputValueClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC368'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputKeyClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC369'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputValueClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC370'><br/></div><div class='line' id='LC371'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapperClass</span><span class="o">(</span><span class="n">CalcDistMap</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC372'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setReducerClass</span><span class="o">(</span><span class="n">CalcDistReduce</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC373'><br/></div><div class='line' id='LC374'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setInputFormatClass</span><span class="o">(</span><span class="n">SequenceFileInputFormat</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC375'>&nbsp;	    <span class="c1">//if (i == ) {</span></div><div class='line' id='LC376'>		<span class="c1">//job.setOutputFormatClass(TextOutputFormat.class);</span></div><div class='line' id='LC377'>	    <span class="c1">//} else{</span></div><div class='line' id='LC378'>	    <span class="n">job</span><span class="o">.</span><span class="na">setOutputFormatClass</span><span class="o">(</span><span class="n">SequenceFileOutputFormat</span><span class="o">.</span><span class="na">class</span><span class="o">);</span> <span class="c1">// CHANGE FOR TEST</span></div><div class='line' id='LC379'>	    <span class="c1">//}</span></div><div class='line' id='LC380'><br/></div><div class='line' id='LC381'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Notice how each mapreduce job gets gets its own output dir</span></div><div class='line' id='LC382'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">FileInputFormat</span><span class="o">.</span><span class="na">addInputPath</span><span class="o">(</span><span class="n">job</span><span class="o">,</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="s">&quot;bfs-&quot;</span> <span class="o">+</span> <span class="n">i</span> <span class="o">+</span> <span class="s">&quot;-out&quot;</span><span class="o">));</span></div><div class='line' id='LC383'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">FileOutputFormat</span><span class="o">.</span><span class="na">setOutputPath</span><span class="o">(</span><span class="n">job</span><span class="o">,</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="s">&quot;bfs-&quot;</span><span class="o">+</span> <span class="o">(</span><span class="n">i</span><span class="o">+</span><span class="mi">1</span><span class="o">)</span> <span class="o">+</span><span class="s">&quot;-out&quot;</span><span class="o">));</span></div><div class='line' id='LC384'><br/></div><div class='line' id='LC385'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">waitForCompletion</span><span class="o">(</span><span class="kc">true</span><span class="o">);</span></div><div class='line' id='LC386'><br/></div><div class='line' id='LC387'>	    <span class="n">System</span><span class="o">.</span><span class="na">out</span><span class="o">.</span><span class="na">println</span><span class="o">(</span><span class="s">&quot;=====================Visiting nodes:&quot;</span> <span class="o">+</span> <span class="n">job</span><span class="o">.</span><span class="na">getCounters</span><span class="o">().</span><span class="na">findCounter</span><span class="o">(</span><span class="n">ValueUse</span><span class="o">.</span><span class="na">CURR_NODE</span><span class="o">).</span><span class="na">getValue</span><span class="o">());</span></div><div class='line' id='LC388'>	    <span class="k">if</span> <span class="o">(</span><span class="n">job</span><span class="o">.</span><span class="na">getCounters</span><span class="o">().</span><span class="na">findCounter</span><span class="o">(</span><span class="n">ValueUse</span><span class="o">.</span><span class="na">CURR_NODE</span><span class="o">).</span><span class="na">getValue</span><span class="o">()</span> <span class="o">==</span> <span class="mi">0</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC389'>		<span class="k">break</span><span class="o">;</span></div><div class='line' id='LC390'>	    <span class="o">}</span></div><div class='line' id='LC391'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">i</span><span class="o">++;</span></div><div class='line' id='LC392'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC393'><br/></div><div class='line' id='LC394'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// Mapreduce config for histogram computation</span></div><div class='line' id='LC395'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span> <span class="o">=</span> <span class="k">new</span> <span class="n">Job</span><span class="o">(</span><span class="n">conf</span><span class="o">,</span> <span class="s">&quot;hist&quot;</span><span class="o">);</span></div><div class='line' id='LC396'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setJarByClass</span><span class="o">(</span><span class="n">SmallWorld</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC397'><br/></div><div class='line' id='LC398'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapOutputKeyClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC399'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapOutputValueClass</span><span class="o">(</span><span class="n">LongWritable</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC400'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputKeyClass</span><span class="o">(</span><span class="n">Text</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC401'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputValueClass</span><span class="o">(</span><span class="n">LongWritable</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC402'><br/></div><div class='line' id='LC403'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setMapperClass</span><span class="o">(</span><span class="n">HistMap</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC404'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setCombinerClass</span><span class="o">(</span><span class="n">HistReduce</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC405'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setReducerClass</span><span class="o">(</span><span class="n">HistReduce</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC406'><br/></div><div class='line' id='LC407'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setInputFormatClass</span><span class="o">(</span><span class="n">SequenceFileInputFormat</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC408'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">setOutputFormatClass</span><span class="o">(</span><span class="n">TextOutputFormat</span><span class="o">.</span><span class="na">class</span><span class="o">);</span></div><div class='line' id='LC409'><br/></div><div class='line' id='LC410'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// By declaring i above outside of loop conditions, can use it</span></div><div class='line' id='LC411'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="c1">// here to get last bfs output to be input to histogram</span></div><div class='line' id='LC412'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">FileInputFormat</span><span class="o">.</span><span class="na">addInputPath</span><span class="o">(</span><span class="n">job</span><span class="o">,</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="s">&quot;bfs-&quot;</span><span class="o">+</span> <span class="n">i</span> <span class="o">+</span><span class="s">&quot;-out&quot;</span><span class="o">));</span></div><div class='line' id='LC413'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">FileOutputFormat</span><span class="o">.</span><span class="na">setOutputPath</span><span class="o">(</span><span class="n">job</span><span class="o">,</span> <span class="k">new</span> <span class="n">Path</span><span class="o">(</span><span class="n">args</span><span class="o">[</span><span class="mi">1</span><span class="o">]));</span></div><div class='line' id='LC414'><br/></div><div class='line' id='LC415'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">job</span><span class="o">.</span><span class="na">waitForCompletion</span><span class="o">(</span><span class="kc">true</span><span class="o">);</span> </div><div class='line' id='LC416'><br/></div><div class='line' id='LC417'>&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class='line' id='LC418'><span class="o">}</span></div></pre></div>
          </td>
        </tr>
      </table>
  </div>

          </div>
        </div>
      </div>

      <a href="#jump-to-line" rel="facebox" data-hotkey="l" class="js-jump-to-line" style="display:none">Jump to Line</a>
      <div id="jump-to-line" style="display:none">
        <h2>Jump to Line</h2>
        <form accept-charset="UTF-8" class="js-jump-to-line-form">
          <input class="textfield js-jump-to-line-field" type="text">
          <div class="full-button">
            <button type="submit" class="classy">
              Go
            </button>
          </div>
        </form>
      </div>

    </div>
</div>

<div class="frame frame-loading large-loading-area" style="display:none;">
  <img src="https://a248.e.akamai.net/assets.github.com/images/spinners/octocat-spinner-128.gif?1347543527" height="64" width="64">
</div>

        </div>
      </div>
      <div class="context-overlay"></div>
    </div>

      <div id="footer-push"></div><!-- hack for sticky footer -->
    </div><!-- end of wrapper - hack for sticky footer -->

      <!-- footer -->
      <div id="footer">
  <div class="container clearfix">

      <dl class="footer_nav">
        <dt>GitHub</dt>
        <dd><a href="https://github.com/about">About us</a></dd>
        <dd><a href="https://github.com/blog">Blog</a></dd>
        <dd><a href="https://github.com/contact">Contact &amp; support</a></dd>
        <dd><a href="http://enterprise.github.com/">GitHub Enterprise</a></dd>
        <dd><a href="http://status.github.com/">Site status</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>Applications</dt>
        <dd><a href="http://mac.github.com/">GitHub for Mac</a></dd>
        <dd><a href="http://windows.github.com/">GitHub for Windows</a></dd>
        <dd><a href="http://eclipse.github.com/">GitHub for Eclipse</a></dd>
        <dd><a href="http://mobile.github.com/">GitHub mobile apps</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>Services</dt>
        <dd><a href="http://get.gaug.es/">Gauges: Web analytics</a></dd>
        <dd><a href="http://speakerdeck.com">Speaker Deck: Presentations</a></dd>
        <dd><a href="https://gist.github.com">Gist: Code snippets</a></dd>
        <dd><a href="http://jobs.github.com/">Job board</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>Documentation</dt>
        <dd><a href="http://help.github.com/">GitHub Help</a></dd>
        <dd><a href="http://developer.github.com/">Developer API</a></dd>
        <dd><a href="http://github.github.com/github-flavored-markdown/">GitHub Flavored Markdown</a></dd>
        <dd><a href="http://pages.github.com/">GitHub Pages</a></dd>
      </dl>

      <dl class="footer_nav">
        <dt>More</dt>
        <dd><a href="http://training.github.com/">Training</a></dd>
        <dd><a href="https://github.com/edu">Students &amp; teachers</a></dd>
        <dd><a href="http://shop.github.com">The Shop</a></dd>
        <dd><a href="http://octodex.github.com/">The Octodex</a></dd>
      </dl>

      <hr class="footer-divider">


    <p class="right">&copy; 2012 <span title="0.09975s from fe16.rs.github.com">GitHub</span> Inc. All rights reserved.</p>
    <a class="left" href="https://github.com/">
      <span class="mega-icon mega-icon-invertocat"></span>
    </a>
    <ul id="legal">
        <li><a href="https://github.com/site/terms">Terms of Service</a></li>
        <li><a href="https://github.com/site/privacy">Privacy</a></li>
        <li><a href="https://github.com/security">Security</a></li>
    </ul>

  </div><!-- /.container -->

</div><!-- /.#footer -->


    

<div id="keyboard_shortcuts_pane" class="instapaper_ignore readability-extra" style="display:none">
  <h2>Keyboard Shortcuts <small><a href="#" class="js-see-all-keyboard-shortcuts">(see all)</a></small></h2>

  <div class="columns threecols">
    <div class="column first">
      <h3>Site wide shortcuts</h3>
      <dl class="keyboard-mappings">
        <dt>s</dt>
        <dd>Focus command bar</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>?</dt>
        <dd>Bring up this help dialog</dd>
      </dl>
    </div><!-- /.column.first -->

    <div class="column middle" style='display:none'>
      <h3>Commit list</h3>
      <dl class="keyboard-mappings">
        <dt>j</dt>
        <dd>Move selection down</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>k</dt>
        <dd>Move selection up</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>c <em>or</em> o <em>or</em> enter</dt>
        <dd>Open commit</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>y</dt>
        <dd>Expand URL to its canonical form</dd>
      </dl>
    </div><!-- /.column.first -->

    <div class="column last js-hidden-pane" style='display:none'>
      <h3>Pull request list</h3>
      <dl class="keyboard-mappings">
        <dt>j</dt>
        <dd>Move selection down</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>k</dt>
        <dd>Move selection up</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>o <em>or</em> enter</dt>
        <dd>Open issue</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> enter</dt>
        <dd>Submit comment</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> shift p</dt>
        <dd>Preview comment</dd>
      </dl>
    </div><!-- /.columns.last -->

  </div><!-- /.columns.equacols -->

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>

    <h3>Issues</h3>

    <div class="columns threecols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt>j</dt>
          <dd>Move selection down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>k</dt>
          <dd>Move selection up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>x</dt>
          <dd>Toggle selection</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o <em>or</em> enter</dt>
          <dd>Open issue</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> enter</dt>
          <dd>Submit comment</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> shift p</dt>
          <dd>Preview comment</dd>
        </dl>
      </div><!-- /.column.first -->
      <div class="column last">
        <dl class="keyboard-mappings">
          <dt>c</dt>
          <dd>Create issue</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>l</dt>
          <dd>Create label</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>i</dt>
          <dd>Back to inbox</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>u</dt>
          <dd>Back to issues</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>/</dt>
          <dd>Focus issues search</dd>
        </dl>
      </div>
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>

    <h3>Issues Dashboard</h3>

    <div class="columns threecols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt>j</dt>
          <dd>Move selection down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>k</dt>
          <dd>Move selection up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o <em>or</em> enter</dt>
          <dd>Open issue</dd>
        </dl>
      </div><!-- /.column.first -->
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>

    <h3>Network Graph</h3>
    <div class="columns equacols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt><span class="badmono">←</span> <em>or</em> h</dt>
          <dd>Scroll left</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="badmono">→</span> <em>or</em> l</dt>
          <dd>Scroll right</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="badmono">↑</span> <em>or</em> k</dt>
          <dd>Scroll up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="badmono">↓</span> <em>or</em> j</dt>
          <dd>Scroll down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>t</dt>
          <dd>Toggle visibility of head labels</dd>
        </dl>
      </div><!-- /.column.first -->
      <div class="column last">
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">←</span> <em>or</em> shift h</dt>
          <dd>Scroll all the way left</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">→</span> <em>or</em> shift l</dt>
          <dd>Scroll all the way right</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">↑</span> <em>or</em> shift k</dt>
          <dd>Scroll all the way up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">↓</span> <em>or</em> shift j</dt>
          <dd>Scroll all the way down</dd>
        </dl>
      </div><!-- /.column.last -->
    </div>
  </div>

  <div class="js-hidden-pane" >
    <div class="rule"></div>
    <div class="columns threecols">
      <div class="column first js-hidden-pane" >
        <h3>Source Code Browsing</h3>
        <dl class="keyboard-mappings">
          <dt>t</dt>
          <dd>Activates the file finder</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>l</dt>
          <dd>Jump to line</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>w</dt>
          <dd>Switch branch/tag</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>y</dt>
          <dd>Expand URL to its canonical form</dd>
        </dl>
      </div>
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>
    <div class="columns threecols">
      <div class="column first">
        <h3>Browsing Commits</h3>
        <dl class="keyboard-mappings">
          <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> enter</dt>
          <dd>Submit comment</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>escape</dt>
          <dd>Close form</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>p</dt>
          <dd>Parent commit</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o</dt>
          <dd>Other parent commit</dd>
        </dl>
      </div>
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>
    <h3>Notifications</h3>

    <div class="columns threecols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt>j</dt>
          <dd>Move selection down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>k</dt>
          <dd>Move selection up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o <em>or</em> enter</dt>
          <dd>Open notification</dd>
        </dl>
      </div><!-- /.column.first -->

      <div class="column second">
        <dl class="keyboard-mappings">
          <dt>e <em>or</em> shift i <em>or</em> y</dt>
          <dd>Mark as read</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift m</dt>
          <dd>Mute thread</dd>
        </dl>
      </div><!-- /.column.first -->
    </div>
  </div>

</div>

    <div id="markdown-help" class="instapaper_ignore readability-extra">
  <h2>Markdown Cheat Sheet</h2>

  <div class="cheatsheet-content">

  <div class="mod">
    <div class="col">
      <h3>Format Text</h3>
      <p>Headers</p>
      <pre>
# This is an &lt;h1&gt; tag
## This is an &lt;h2&gt; tag
###### This is an &lt;h6&gt; tag</pre>
     <p>Text styles</p>
     <pre>
*This text will be italic*
_This will also be italic_
**This text will be bold**
__This will also be bold__

*You **can** combine them*
</pre>
    </div>
    <div class="col">
      <h3>Lists</h3>
      <p>Unordered</p>
      <pre>
* Item 1
* Item 2
  * Item 2a
  * Item 2b</pre>
     <p>Ordered</p>
     <pre>
1. Item 1
2. Item 2
3. Item 3
   * Item 3a
   * Item 3b</pre>
    </div>
    <div class="col">
      <h3>Miscellaneous</h3>
      <p>Images</p>
      <pre>
![GitHub Logo](/images/logo.png)
Format: ![Alt Text](url)
</pre>
     <p>Links</p>
     <pre>
http://github.com - automatic!
[GitHub](http://github.com)</pre>
<p>Blockquotes</p>
     <pre>
As Kanye West said:

> We're living the future so
> the present is our past.
</pre>
    </div>
  </div>
  <div class="rule"></div>

  <h3>Code Examples in Markdown</h3>
  <div class="col">
      <p>Syntax highlighting with <a href="http://github.github.com/github-flavored-markdown/" title="GitHub Flavored Markdown" target="_blank">GFM</a></p>
      <pre>
```javascript
function fancyAlert(arg) {
  if(arg) {
    $.facebox({div:'#foo'})
  }
}
```</pre>
    </div>
    <div class="col">
      <p>Or, indent your code 4 spaces</p>
      <pre>
Here is a Python code example
without syntax highlighting:

    def foo:
      if not bar:
        return true</pre>
    </div>
    <div class="col">
      <p>Inline code for comments</p>
      <pre>
I think you should use an
`&lt;addr&gt;` element here instead.</pre>
    </div>
  </div>

  </div>
</div>


    <div id="ajax-error-message" class="flash flash-error">
      <span class="mini-icon mini-icon-exclamation"></span>
      Something went wrong with that request. Please try again.
      <a href="#" class="mini-icon mini-icon-remove-close ajax-error-dismiss"></a>
    </div>

    
    
    <span id='server_response_time' data-time='0.10114' data-host='fe16'></span>
    
  </body>
</html>

