<%@ include file="../common/header.jspf" %>

	<div class="row">
		<a href="/eventline">Back</a>
		<h3>${monthName}</h3>
	</div>

	<div class="row">
		<p> Jump to day 
			<c:forEach var = "i" begin = "1" end = "${monthDays}">
				<a href="#month${i}">${i}</a>
			</c:forEach>
		</p>
	</div>
	
	${todosMap}

	<c:forEach items="${eventsMap}" var="entry">
        <c:set var="i" value="${entry.key}" />
        <c:set var="value" value="${entry.value}" />
        
		<div>
			<div>
				<h1 id="month${i}">${i}</a></h1>
			</div>
            <c:forEach items="${value}" var="event">
                <div class="my-1">
					<div class="card">
						<div class="card-body">
							<div class="crayons-story__body">
								<div class="crayons-story__indention">
								<h2 class="crayons-story__title">
									<a href="/todo?id=${event.id}" id="article-link-1766481">${event.name}</a>
								</h2>
								<div class="crayons-story__tags">
									<a class="crayons-tag crayons-tag--filled  " style="
									--tag-bg: rgba(113, 234, 139, 0.10);
									--tag-prefix: #71EA8B;
									--tag-bg-hover: rgba(113, 234, 139, 0.10);
									--tag-prefix-hover: #71EA8B;
								" href="/t/discuss"><span class="crayons-tag__prefix">#</span>discuss</a>
									<a class="crayons-tag  crayons-tag--monochrome " style="
									--tag-bg: rgba(42, 37, 102, 0.10);
									--tag-prefix: #2A2566;
									--tag-bg-hover: rgba(42, 37, 102, 0.10);
									--tag-prefix-hover: #2A2566;
								" href="/t/career"><span class="crayons-tag__prefix">#</span>career</a>
									<a class="crayons-tag  crayons-tag--monochrome " style="
									--tag-bg: rgba(59, 73, 223, 0.10);
									--tag-prefix: #3b49df;
									--tag-bg-hover: rgba(59, 73, 223, 0.10);
									--tag-prefix-hover: #3b49df;
								" href="/t/advice"><span class="crayons-tag__prefix">#</span>advice</a>
									<a class="crayons-tag  crayons-tag--monochrome " style="
									--tag-bg: rgba(0, 131, 53, 0.10);
									--tag-prefix: #008335;
									--tag-bg-hover: rgba(0, 131, 53, 0.10);
									--tag-prefix-hover: #008335;
								" href="/t/beginners"><span class="crayons-tag__prefix">#</span>beginners</a>
								</div>
								<div class="crayons-story__bottom">
									<div class="row">
										<div class="col">
											<div class="crayons-story__details">
												<a href="/eventline/month/1/day/1" class="crayons-btn crayons-btn--s crayons-btn--ghost crayons-btn--icon-left" data-reaction-count="" data-reactable-id="1766481">
													<div class="multiple_reactions_aggregate">
														<span class="multiple_reactions_icons_container" dir="rtl">
															<span class="crayons_icon_container">
																<img src="https://dev.to/assets/fire-f60e7a582391810302117f987b22a8ef04a2fe0df7e3258a5f49332df1cec71e.svg" width="18" height="18">
															</span>
															<span class="crayons_icon_container">
																<img src="https://dev.to/assets/raised-hands-74b2099fd66a39f2d7eed9305ee0f4553df0eb7b4f11b01b6b1b499973048fe5.svg" width="18" height="18">
															</span>
															<span class="crayons_icon_container">
																<img src="https://dev.to/assets/exploding-head-daceb38d627e6ae9b730f36a1e390fca556a4289d5a41abb2c35068ad3e2c4b5.svg" width="18" height="18">
															</span>
															<span class="crayons_icon_container">
																<img src="https://dev.to/assets/multi-unicorn-b44d6f8c23cdd00964192bedc38af3e82463978aa611b4365bd33a0f1f4f3e97.svg" width="18" height="18">
															</span>
															<span class="crayons_icon_container">
																<img src="https://dev.to/assets/sparkle-heart-5f9bee3767e18deb1bb725290cb151c25234768a0e9a2bd39370c382d02920cf.svg" width="18" height="18">
															</span>
														</span>
													<span class="aggregate_reactions_counter">195<span class="hidden s:inline">&nbsp;reactions</span></span>
													</div>
												</a>
											</div>
										</div>
										<div class="col">
											<div>
											<a href="/eventline/month/1/day/1" class="crayons-btn crayons-btn--s crayons-btn--ghost crayons-btn--icon-left flex items-center">
												<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" role="img" aria-labelledby="anpno4n4jwsnoyuf73ijaz3mnqfbhhv4" class="crayons-icon"><title id="anpno4n4jwsnoyuf73ijaz3mnqfbhhv4">Comments</title><path d="M10.5 5h3a6 6 0 110 12v2.625c-3.75-1.5-9-3.75-9-8.625a6 6 0 016-6zM12 15.5h1.5a4.501 4.501 0 001.722-8.657A4.5 4.5 0 0013.5 6.5h-3A4.5 4.5 0 006 11c0 2.707 1.846 4.475 6 6.36V15.5z"></path></svg>
												27<span class="hidden s:inline">&nbsp;comments</span>
												</a>
											</div>
										</div>
										<div class="col">
											notify me | ${event.user.username}
										</div>
									</div>
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>
            </c:forEach>
    </c:forEach>

<%@ include file="../common/footer.jspf" %>