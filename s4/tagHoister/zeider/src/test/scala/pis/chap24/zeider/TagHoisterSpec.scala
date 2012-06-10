package pis.chap24.zeider

import org.scalatest.Spec
import org.scalatest.BeforeAndAfter

class TagHoisterSpec extends Spec with BeforeAndAfter {
	var th:TagHoister = null
	var bookmarks:List[Bookmark] = List.empty
	var a:Bookmark = null
	var b:Bookmark = null
	var c:Bookmark = null
	
	before {
	  th = new TagHoister
	  a = new Bookmark("outsider", "user", "user", "http://google.com", List("search", "google", "engine"))
	  b = new Bookmark("outsider", "user", "user", "http://daum.com", List("search", "daum", "portal", "engine"))
	  c = new Bookmark("outsider", "user", "user", "http://naver.com", List("search", "naver", "portal"))
	  
	  bookmarks = List(a, b, c) 
	}
	
	describe("Tag 추출") {
		it("북마크리스트에서 전체 태그를 추출한다") {
			val tags = th.extractAllTags(bookmarks)
			assert(tags === List("search", "google", "engine", "search", "daum", "portal", "engine", "search", "naver", "portal"))
		}
		it("전체태그에서 중복을 제거한다") {
		    val tags = th.removeDuplicatedTags(bookmarks)
		    assert(tags === Set("google", "engine", "search", "daum", "portal", "naver"))
		}
		it("전체 태그에서 2번이상 사용된 유니크 태그를 추출한다") {
			val tags = th.findTagsTwoMoreUsed(bookmarks)
			assert(tags === Set("search", "portal", "engine"))
		}
		it("태그그룹 후보군 찾기") {
			val tagGroup = th.findCandidateTagGroup(bookmarks)
			assert(tagGroup === List(Set("engine", "search"), Set("search", "portal"), Set("portal", "engine")))
		}
		it("태그그룹별 소속된 북마크 찾기") {
			val bookmarkGroup = th.findNotTheSameButSimilarGroup(bookmarks)
			assert(bookmarkGroup === Map(Set("engine", "search") -> Set(a, b), Set("search", "portal") -> Set(b, c)))
		}
	}
	
}